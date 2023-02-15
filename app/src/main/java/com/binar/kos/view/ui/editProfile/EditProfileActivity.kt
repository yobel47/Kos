package com.binar.kos.view.ui.editProfile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.binar.kos.databinding.ActivityEditProfileBinding
import com.binar.kos.utils.Status
import com.binar.kos.utils.hideLoading
import com.binar.kos.utils.showLoading
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.LogoutViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileActivity: AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val logoutViewModel: LogoutViewModel by viewModel()
    private val dataStore: DatastoreViewModel by viewModel()

    var accessToken: String = ""

    var userId: Int = 0
    var namaLengkap: String = ""
    var gender: String = ""
    var tanggalLahir: Long = 0
    var asalKota: String = ""
    var marriageStatus: String = ""
    var profession: String = ""
    var bankName: String = ""
    var bankAccountNumber: String = ""
    var bankAccountName: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUsername()


//        uiListener()
    }

    private fun getUsername() {
        dataStore.getAccessToken().observe(this) { token ->
            if (!token.equals("default value")) {
                logoutViewModel.getUsedata(token).observe(this) { result ->
                    when (result.status) {
                        Status.LOADING -> {
                            showLoading(this)
                        }
                        Status.SUCCESS -> {
                            hideLoading()
                            if (result.data?.data!!.id !== null){
                                userId = result.data?.data!!.id!!
                            }
                        }
                        Status.ERROR -> {
                            showLoading(this)
                            Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }


//    private fun uiListener() {
//        inputTanggalLahirListener()
//
//        selectBankListener()
//
//        inputListener(binding.inputNamaLengkap) { text -> namaLengkap = text }
//
//        inputListener(binding.inputAsalKota) { text -> asalKota = text }
//
//        inputListener(binding.inputProfesi) { text -> profession = text }
//
//        inputListener(binding.inputNoRek) { text -> bankAccountNumber = text }
//
//        inputListener(binding.inputNamaPemilikBank) { text -> bankAccountName = text }
//
//        radioListener(binding.genderPicker) { text -> gender = text }
//
//        radioListener(binding.marriageStatusPicker) { text -> marriageStatus = text }
//
//        onChangeUser()
//
//    }

//    private fun inputTanggalLahirListener() {
//        binding.inputTanggalLahir.setOnClickListener {
//            val cal = Calendar.getInstance()
//
//            val year = cal.get(Calendar.YEAR)
//            val month = cal.get(Calendar.MONTH)
//            val day = cal.get(Calendar.DAY_OF_MONTH)
//
//            val datePickerDialog = DatePickerDialog(
//                this,
//                { view, year, monthOfYear, dayOfMonth ->
////                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
////
////                    val date = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
////                    val localDateTime = LocalDateTime.of(date, LocalDateTime.MIN.toLocalTime())
////                    val epochTime = localDateTime.toEpochSecond(java.time.ZoneOffset.UTC)
////
////                    tanggalLahir = epochTime
////
////                    binding.inputTanggalLahir.setText(dat)
//                },
//                year,
//                month,
//                day
//            )
//            datePickerDialog.show()
//        }
//    }

//    private fun radioListener(radioGroup: RadioGroup, listener: (String) -> Unit) {
//        radioGroup.setOnCheckedChangeListener { group, checkedId ->
//            val radio: RadioButton = findViewById(checkedId)
//            listener(radio.text.toString())
//        }
//    }

//    private fun inputListener(inputText: TextInputEditText, listener: (String) -> Unit) {
//        inputText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                // No implementation needed
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                // No implementation needed
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                listener(s.toString())
//            }
//        })
//    }

//    private fun selectBankListener() {
//        binding.bankPicker.onItemSelectedListener = object : OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                // Do something when a new item is selected
//                val selectedItem = parent?.getItemAtPosition(position).toString()
//                if (selectedItem != "Pilih Bank") bankName = selectedItem
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                // no implementation
//            }
//        }
//    }

//    private fun onChangeUser() {
//
//        binding.saveButton.setOnClickListener {
//            val editUserRequest = EditUserRequest(
//                System.currentTimeMillis(),
//                System.currentTimeMillis(),
//                namaLengkap,
//                gender,
//                "",
//                asalKota,
//                marriageStatus,
//                "0",
//                profession,
//                bankName,
//                bankAccountNumber,
//                "0",
//                ""
//            )
//            editProfileViewmodel.editThisUser(0, editUserRequest, accessToken).observe(this){result ->
//                when(result.status){
//                    Status.LOADING -> {}
//                    Status.SUCCESS -> {
//                        Toast.makeText(this, "User berhasil update", Toast.LENGTH_LONG).show()
//                        val intent = Intent(this, HomeActivity::class.java)
//                        finish()
//                        startActivity(intent)
//                    }
//                    Status.ERROR -> {
//                        Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
//                    }
//                }
//            }
//        }
//    }
}

