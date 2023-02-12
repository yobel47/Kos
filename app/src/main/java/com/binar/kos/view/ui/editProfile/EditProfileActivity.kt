package com.binar.kos.view.ui.editProfile

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.binar.kos.data.remote.request.EditUserRequest
import com.binar.kos.databinding.ActivityEditProfileBinding
import com.binar.kos.utils.Status
import com.binar.kos.view.ui.home.HomeActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.EditProfileViewModel
import com.google.android.material.textfield.TextInputEditText
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class EditProfileActivity: AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val editProfileViewmodel: EditProfileViewModel by viewModel()
    private val dataStore: DatastoreViewModel by viewModel()

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

        getThisUser()

        uiListener()
    }

    private fun getThisUser() {
        dataStore.getAccessToken().observe(this) { token ->
            if (!token.equals("default value")) {
                editProfileViewmodel.getThisUser(token).observe(this) { result ->
                    when (result.status) {
                        Status.LOADING -> {}
                        Status.SUCCESS -> {
                            userId = result.data!!.data!!.id ?: 0
                            Log.e("HELLO", result.data!!.data!!.id.toString())
                            binding.inputNamaLengkap.setText(result.data!!.data!!.namaLengkap)
                        }
                        Status.ERROR -> {
                            Toast.makeText(this, "Failed to fetch user data", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun uiListener() {
        inputTanggalLahirListener()

        selectBankListener()

        inputListener(binding.inputNamaLengkap) { text -> namaLengkap = text }

        inputListener(binding.inputAsalKota) { text -> asalKota = text }

        inputListener(binding.inputProfesi) { text -> profession = text }

        inputListener(binding.inputNoRek) { text -> bankAccountNumber = text }

        inputListener(binding.inputNamaPemilikBank) { text -> bankAccountName = text }

        radioListener(binding.genderPicker) { text -> gender = text }

        radioListener(binding.marriageStatusPicker) { text -> marriageStatus = text }

        onChangeUser()

    }

    private fun inputTanggalLahirListener() {
        binding.inputTanggalLahir.setOnClickListener {
            val cal = Calendar.getInstance()

            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)

                    val date = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
                    val localDateTime = LocalDateTime.of(date, LocalDateTime.MIN.toLocalTime())
                    val epochTime = localDateTime.toEpochSecond(java.time.ZoneOffset.UTC)

                    tanggalLahir = epochTime

                    binding.inputTanggalLahir.setText(dat)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }

    private fun radioListener(radioGroup: RadioGroup, listener: (String) -> Unit) {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            listener(radio.text.toString())
        }
    }

    private fun inputListener(inputText: TextInputEditText, listener: (String) -> Unit) {
        inputText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No implementation needed
            }

            override fun afterTextChanged(s: Editable?) {
                listener(s.toString())
            }
        })
    }

    private fun selectBankListener() {
        binding.bankPicker.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
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
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedTanggalLahir = format.format(tanggalLahir)
        var accessToken: String = ""

        binding.saveButton.setOnClickListener {
            val editUserRequest = EditUserRequest(
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                namaLengkap,
                gender,
                formattedTanggalLahir,
                asalKota,
                marriageStatus,
                "0",
                profession,
                bankName,
                bankAccountNumber,
                "0",
                ""
            )

            dataStore.getAccessToken().observe(this) { token ->
                if (!token.equals("default value")) {
                    accessToken = token
                }
            }

            editProfileViewmodel.editThisUser(0, editUserRequest, accessToken).observe(this){result ->
                when(result.status){
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        Toast.makeText(this, "User updated!", Toast.LENGTH_LONG)
                        val intent = Intent(this, HomeActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG)
                    }
                }
            }
        }
    }
}

