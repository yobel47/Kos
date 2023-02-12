package com.binar.kos.view.ui.transactionGuide

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.binar.kos.databinding.ActivityTransactionBinding
import com.binar.kos.databinding.ActivityTransactionGuideBinding
import com.binar.kos.databinding.CardChooseImageBinding
import com.binar.kos.databinding.SentDialog2Binding
import com.binar.kos.utils.*
import com.binar.kos.view.ui.add.AddRoomActivity
import com.binar.kos.view.ui.booking.BookingActivity
import com.binar.kos.view.ui.home.HomeActivity
import com.binar.kos.view.ui.homePenyewa.HomePenyewaActivity
import com.binar.kos.view.ui.transaction.TransactionActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.RoomViewModel
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class TransactionGuideActivity : AppCompatActivity() {

    private val roomViewModel: RoomViewModel by viewModel()
    private val dataStore: DatastoreViewModel by viewModel()
    private lateinit var binding: ActivityTransactionGuideBinding
    private lateinit var clipboardManager: ClipboardManager
    private lateinit var isdialogg: AlertDialog
    private lateinit var isdialoggg: AlertDialog
    var bookingId = 0
    var paymentId = 0
    var paymentMethod = ""
    var paymentBankType = ""
    var paymentContent = ""
    var accessToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUpload.isEnabled = false

        binding.btnBack.setOnClickListener {
            finish()
        }


        binding.cvImageDefault.setOnClickListener {
            setupDialog()
        }
        onSetup()

        binding.btnUpload.setOnClickListener {
            onUpload()
        }

        binding.cvCopy.setOnClickListener {
            clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText(COPY, binding.tvCopyGuide2.text.toString())
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(applicationContext, "Copied", Toast.LENGTH_SHORT).show()
        }
    }


    private fun onUpload(){
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        if (getFile?.exists() == true) {
            val file = reduceFileImage(getFile as File)
            builder.addFormDataPart("picture",
                file.name,
                reduceFileImage(file).asRequestBody("image/jpeg".toMediaTypeOrNull()))
        }
        val requestBody = builder.build()
        roomViewModel.uploadTransactionFile(
            accessToken,
            bookingId,
            requestBody
        ).observe(this) { result ->
            when (result.status) {
                Status.LOADING -> {
                    showLoading(this)
                }
                Status.SUCCESS -> {
                    hideLoading()

                    val binding2: SentDialog2Binding =
                        SentDialog2Binding.inflate(LayoutInflater.from(this))
                    val builder2 = AlertDialog.Builder(this)
                    builder2.setView(binding2.root)
                    binding2.tvTittleSent.text = "Bukti Bayar Berhasil Di-Upload"
                    binding2.tvSent.text = "Bukti pembayaranmu udah kami terima untuk dicek dan diproses yaa!\n" +
                            "Tunggu ya, maks 30 menit lagi,\n" +
                            "kami bakal kirim kartu kostmu :)"
                    binding2.btnCheck.text = "Cek Pesanan"
                    builder2.setCancelable(false)
                    binding2.btnClose.setOnClickListener {
                        isdialoggg.dismiss()
                        val intent = Intent(this, HomeActivity::class.java)
                        finishAffinity()
                        startActivity(intent)
                    }
                    val displayMetrics = DisplayMetrics()
                    @Suppress("DEPRECATION")
                    windowManager.defaultDisplay.getMetrics(displayMetrics)
                    val width = displayMetrics.widthPixels
                    isdialoggg = builder2.create()
                    isdialoggg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    isdialoggg.show()
                    val window: Window = isdialoggg.window!!
                    val wlp: WindowManager.LayoutParams = window.attributes
                    wlp.width = (width * 0.8).toInt()
                    window.attributes = wlp
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

    private fun onSetup() {
        bookingId = intent.getIntExtra(TransactionActivity.BOOKING_ID, 0)
        paymentId = intent.getIntExtra(TransactionActivity.PAYMENT_ID, 0)
        paymentMethod = intent.getStringExtra(TransactionActivity.PAYMENT_METHOD).toString()
        paymentBankType = intent.getStringExtra(TransactionActivity.PAYMENT_BANK_TYPE).toString()
        paymentContent = intent.getStringExtra(TransactionActivity.PAYMENT_CONTENT).toString()

        binding.tvCopyGuide2.text = paymentContent

        dataStore.getAccessToken().observe(this) { token ->
            if (!token.equals("default value")) {
                accessToken = token
            }
        }
    }

    private fun openGallery() {
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
                this@TransactionGuideActivity,
                "com.binar.kos.view.ui",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun allPermissionsGranted() = TransactionGuideActivity.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == TransactionGuideActivity.REQUEST_CODE_PERMISSIONS) {
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
            binding.imageContainer.visibility = View.GONE
            binding.imagePhoto.visibility = View.VISIBLE
            val myFile = File(currentPhotoPath)
            getFile = myFile
            val result = BitmapFactory.decodeFile(myFile.path)
            Glide.with(this)
                .load(result)
                .into(binding.imagePhoto)
            binding.btnUpload.isEnabled = true
        } else {
            if (getFile?.exists() != true) {
                binding.imageContainer.visibility = View.VISIBLE
                binding.imagePhoto.visibility = View.GONE
            }
        }
    }


    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            binding.imageContainer.visibility = View.GONE
            binding.imagePhoto.visibility = View.VISIBLE
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@TransactionGuideActivity)
            getFile = myFile
            Glide.with(this)
                .load(myFile)
                .into(binding.imagePhoto)
            binding.btnUpload.isEnabled = true
        } else {
            if (getFile?.exists() != true) {
                binding.imageContainer.visibility = View.VISIBLE
                binding.imagePhoto.visibility = View.GONE
            }
        }
    }

    private var getFile: File? = null

    companion object {
        const val COPY = "copy"
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}