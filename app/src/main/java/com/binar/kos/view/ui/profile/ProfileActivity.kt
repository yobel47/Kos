package com.binar.kos.view.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.binar.kos.R
import com.binar.kos.data.remote.request.EditUserRequest
import com.binar.kos.databinding.ActivityProfileBinding
import com.binar.kos.databinding.CardChooseImageBinding
import com.binar.kos.utils.*
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.EditProfileViewModel
import com.binar.kos.viewmodel.LogoutViewModel
import com.binar.kos.viewmodel.MainViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val logoutViewModel: LogoutViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()
    private val dataStore: DatastoreViewModel by viewModel()
    private val editProfileViewmodel: EditProfileViewModel by viewModel()

    private lateinit var isdialogg: AlertDialog

    var accessToken = ""

    var userId = 0
    var namaLengkap = ""
    var gender = ""
    var tanggalLahir = ""
    var asalKota = ""
    var noTelp = ""
    var status = ""
    var profesi = ""
    var bankName = ""
    var bankAccountNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivPencil.bringToFront()

        selectBankListener()
        getUsername()
        onSetup()
        binding.inputTanggalLahir.setOnClickListener {
            setupDate()
        }
        onChangeUser()

        binding.ivProfile.setOnClickListener {
            setupDialog()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun onSetup() {
        binding.inputNamaLengkap.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                namaLengkap = binding.inputNamaLengkap.editText?.text.toString()
            }
        })
        binding.inputAsalKota.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                asalKota = binding.inputAsalKota.editText?.text.toString()
            }
        })
        binding.inputNo.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                noTelp = binding.inputNo.editText?.text.toString()
            }
        })
        binding.inputProfesi.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                profesi = binding.inputProfesi.editText?.text.toString()
            }
        })
        binding.inputNoRek.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                bankAccountNumber = binding.inputNoRek.editText?.text.toString()
            }
        })


        binding.lakilaki.setOnCheckedChangeListener { _, b ->
            if (b) {
                gender = "laki-laki"
            }
        }
        binding.perempuan.setOnCheckedChangeListener { _, b ->
            if (b) {
                gender = "perempuan"
            }
        }
        binding.lajang.setOnCheckedChangeListener { _, b ->
            if (b) {
                status = "lajang"
            }
        }
        binding.menikah.setOnCheckedChangeListener { _, b ->
            if (b) {
                status = "menikah"
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun getUsername() {
        dataStore.getAccessToken().observe(this) { token ->
            if (!token.equals("default value")) {
                accessToken = token
                logoutViewModel.getUsedata(token).observe(this) { result ->
                    when (result.status) {
                        Status.LOADING -> {
                            showLoading(this)
                        }
                        Status.SUCCESS -> {
                            hideLoading()
                            if (!result.data?.data?.namaLengkap.isNullOrEmpty()) {
                                namaLengkap = result.data?.data?.namaLengkap!!
                                binding.inputNamaLengkap.editText?.setText(result.data.data.namaLengkap)
                            }
                            if (!result.data?.data?.gender.isNullOrEmpty()) {
                                gender = result.data?.data?.gender!!.lowercase(Locale.getDefault())
                                if (gender == "laki-laki") {
                                    binding.lakilaki.isChecked = true
                                } else if (gender == "perempuan") {
                                    binding.perempuan.isChecked = true
                                }
                            }
                            if (!result.data?.data?.tanggalLahiranFormatted.isNullOrEmpty()) {
                                tanggalLahir = result.data?.data?.tanggalLahiranFormatted!!
                                binding.inputTanggalLahir.text = tanggalLahir
                            }
                            if (!result.data?.data?.kotaAsal.isNullOrEmpty()) {
                                asalKota = result.data?.data?.kotaAsal!!
                                binding.inputAsalKota.editText?.setText(asalKota)
                            }
                            if (!result.data?.data?.noTelepon.isNullOrEmpty()) {
                                noTelp = result.data?.data?.noTelepon!!
                                binding.inputNo.editText?.setText(noTelp)
                            }
                            if (!result.data?.data?.status.isNullOrEmpty()) {
                                status = result.data?.data?.status!!.lowercase(Locale.getDefault())
                                if (status == "lajang") {
                                    binding.lajang.isChecked = true
                                } else if (status == "menikah") {
                                    binding.menikah.isChecked = true
                                }
                            }
                            if (!result.data?.data?.profesi.isNullOrEmpty()) {
                                profesi = result.data?.data?.profesi!!
                                binding.inputProfesi.editText?.setText(profesi)
                            }
                            if (!result.data?.data?.bank.isNullOrEmpty()) {
                                bankName = result.data?.data?.bank!!
                                binding.bankPicker.setSelection(1)
                            }
                            if (!result.data?.data?.noRekening.isNullOrEmpty()) {
                                bankAccountNumber = result.data?.data?.noRekening!!
                                binding.inputNoRek.editText?.setText(bankAccountNumber)
                            }
                            if(!result.data?.data?.image.isNullOrEmpty()){
                                binding.ivPencil.visibility = View.GONE
                                val requestOptions = RequestOptions().placeholder(R.drawable.profile_empty)
                                Glide.with(this).load(result.data?.data?.image).apply(requestOptions).skipMemoryCache(true)
                                    .into(binding.ivProfile)
                            }
                        }
                        Status.ERROR -> {
                            showLoading(this)
                            Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun setupDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        @Suppress("DEPRECATION") val datePickerDialog = DatePickerDialog(
            this,
            { _, mYear, monthOfYear, dayOfMonth ->
                val date = Date(mYear, monthOfYear, dayOfMonth - 1)
                val formatMonth2 = SimpleDateFormat("MM")
                val stringMonth2 = formatMonth2.format(date)
                @Suppress("NAME_SHADOWING") val formatDay = SimpleDateFormat("dd")
                val stringDay = formatDay.format(date)
                tanggalLahir = "$stringDay-$stringMonth2-$mYear"
                binding.inputTanggalLahir.text = tanggalLahir
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun selectBankListener() {
        binding.bankPicker.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                // Do something when a new item is selected
                val selectedItem = parent?.getItemAtPosition(position).toString()
                if (selectedItem != "Pilih Bank") bankName = selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // no implementation
            }
        }
    }
    private fun onChangeUser() {

        binding.saveButton.setOnClickListener {
            val editUserRequest = EditUserRequest(
                namaLengkap,
                gender,
                "08-02-2022",
                asalKota,
                status,
                noTelp,
                profesi,
                bankName,
                bankAccountNumber,
                "0",
                ""
            )
            editProfileViewmodel.editThisUser(editUserRequest, accessToken).observe(this){result ->
                when(result.status){
                    Status.LOADING -> {
                        showLoading(this)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        Toast.makeText(this, "User berhasil update", Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR -> {
                        hideLoading()
                        Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun onUpload(){
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        if (getFile?.exists() == true) {
            val file = reduceFileImage(getFile as File)
            builder.addFormDataPart("imageProfile",
                file.name,
                reduceFileImage(file).asRequestBody("image/jpeg".toMediaTypeOrNull()))
        }
        val requestBody = builder.build()
        mainViewModel.uploadProfile(
            accessToken,
            requestBody
        ).observe(this) { result ->
            when (result.status) {
                Status.LOADING -> {
                    showLoading(this)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    Toast.makeText(this, "Upload foto profil berhasil!", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    hideLoading()
                    Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun setupDialog() {
        val mediaBinding: CardChooseImageBinding =
            CardChooseImageBinding.inflate(LayoutInflater.from(this))
        val builder = AlertDialog.Builder(this)
        builder.setView(mediaBinding.root)
        builder.setCancelable(true)
        isdialogg = builder.create()
        isdialogg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isdialogg.show()
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val window: Window = isdialogg.window!!
        val wlp: WindowManager.LayoutParams = window.attributes
        wlp.gravity = Gravity.BOTTOM
        wlp.width = width
        window.attributes = wlp
        mediaBinding.btnCamera.setOnClickListener {
            isdialogg.dismiss()
            openCamera()
        }
        mediaBinding.btnGallery.setOnClickListener {
            isdialogg.dismiss()
            openGallery()
        }
    }


    private fun openGallery(){
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.binar.kos.view.ui",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }

    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile
            val result =  BitmapFactory.decodeFile(myFile.path)
            Glide.with(this)
                .load(result)
                .into(binding.ivProfile)
            binding.ivPencil.visibility = View.GONE
            onUpload()
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            getFile = myFile
            Glide.with(this)
                .load(myFile)
                .into(binding.ivProfile)
            binding.ivPencil.visibility = View.GONE
            onUpload()
        }
    }

    private var getFile: File? = null

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

}