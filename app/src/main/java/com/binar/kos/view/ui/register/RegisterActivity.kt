package com.binar.kos.view.ui.register

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import com.binar.kos.R
import com.binar.kos.databinding.ActivityRegisterBinding
import com.binar.kos.utils.Status
import com.binar.kos.view.ui.selectUser.SelectUserActivity
import com.binar.kos.view.ui.login.LoginActivity
import com.binar.kos.view.ui.verification.VerificationActivity
import com.binar.kos.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userType = intent.getStringExtra(SelectUserActivity.USER_TYPE)

        checkUserType(userType!!)
        binding.btnRegister.isEnabled = false

        checkButton()
        checkInput()
        onRegister()

        binding.tvBtnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra(SelectUserActivity.USER_TYPE, userType)
            finish()
            startActivity(intent)
        }
    }

    private fun checkInput() {
        val space = Regex("^\\S+\$")
        val special = Regex("^[a-zA-Z0-9_]+\$")
        val letterspace = Regex("^[a-zA-Z ]*\$")
        binding.etName.editText?.doOnTextChanged { text, _, _, _ ->
            if (text?.length!! < 1) {
                binding.etName.error = "Nama tidak boleh kosong"
            } else if (!text.matches(letterspace)) {
                binding.etName.error = "Nama hanya boleh berisi huruf dan spasi"
            } else {
                binding.etName.error = null
                binding.etName.isErrorEnabled = false
            }
        }
        binding.etUsername.editText?.doOnTextChanged { text, _, _, _ ->
            if (text?.length!! < 1) {
                binding.etUsername.error = "Username tidak boleh kosong"
            } else if (text.length < 6) {
                binding.etUsername.error = "Username harus berisi 6 karakter"
            } else if (!text.matches(space)) {
                binding.etUsername.error = "Username tidak boleh berisi spasi"
            } else if (!text.matches(special)) {
                binding.etUsername.error = "Username tidak boleh berisi spesial karakter"
            } else {
                binding.etUsername.error = null
                binding.etUsername.isErrorEnabled = false
            }
        }
    }

    private fun checkUserType(userType: String) {
        if (userType == "pencari") {
            binding.tvWelcomeTitle.text = resources.getString(R.string.daftar_sebagai_pencari_kos)
            binding.ivLogin1.setImageDrawable(ResourcesCompat.getDrawable(resources,
                R.drawable.image_login_1,
                null))
        } else if (userType == "penyewa") {
            binding.tvWelcomeTitle.text = resources.getString(R.string.daftar_sebagai_penyewa_kos)
            binding.ivLogin1.setImageDrawable(ResourcesCompat.getDrawable(resources,
                R.drawable.image_login_2,
                null))
        }
    }


    private fun checkButton() {

        binding.etEmail.editText?.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    if (binding.etEmail.editText?.text!!.isEmpty()) {
                        binding.etEmail.error = "Kamu belum isi email-mu"
                    }
                }
            }

        binding.etPassword.editText?.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    if (binding.etPassword.editText?.text!!.isEmpty()) {
                        binding.etPassword.error = "Kamu belum isi password-mu"
                    }
                }
            }

        binding.etName.editText?.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    if (binding.etName.editText?.text!!.isEmpty()) {
                        binding.etName.error = "Nama tidak boleh kosong!"
                    }
                }
            }

        binding.etUsername.editText?.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    if (binding.etUsername.editText?.text!!.isEmpty()) {
                        binding.etUsername.error = "Username tidak boleh kosong!"
                    }
                }
            }

        binding.etName.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (!binding.etName.isErrorEnabled && !binding.etUsername.isErrorEnabled && !binding.etEmail.isErrorEnabled && !binding.etPassword.isErrorEnabled && binding.cbPolicy.isChecked) {
                    binding.btnRegister.isEnabled = !binding.etName.isErrorEnabled
                } else {
                    binding.btnRegister.isEnabled = false
                }
            }
        })
        binding.etUsername.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (!binding.etName.isErrorEnabled && !binding.etUsername.isErrorEnabled && !binding.etEmail.isErrorEnabled && !binding.etPassword.isErrorEnabled && binding.cbPolicy.isChecked) {
                    binding.btnRegister.isEnabled = !binding.etUsername.isErrorEnabled
                } else {
                    binding.btnRegister.isEnabled = false
                }
            }
        })
        binding.etEmail.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (!binding.etName.isErrorEnabled && !binding.etUsername.isErrorEnabled && !binding.etEmail.isErrorEnabled && !binding.etPassword.isErrorEnabled && binding.cbPolicy.isChecked) {
                    binding.btnRegister.isEnabled = !binding.etEmail.isErrorEnabled
                } else {
                    binding.btnRegister.isEnabled = false
                }
            }
        })
        binding.etPassword.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (!binding.etName.isErrorEnabled && !binding.etUsername.isErrorEnabled && !binding.etEmail.isErrorEnabled && !binding.etPassword.isErrorEnabled && binding.cbPolicy.isChecked) {
                    binding.btnRegister.isEnabled = !binding.etPassword.isErrorEnabled
                } else {
                    binding.btnRegister.isEnabled = false
                }
            }
        })
        binding.cbPolicy.setOnCheckedChangeListener { _, b ->
            binding.btnRegister.isEnabled =
                (binding.etName.editText?.text?.length!! > 1 && !binding.etName.isErrorEnabled) &&
                        (binding.etUsername.editText?.text?.length!! > 1 && !binding.etUsername.isErrorEnabled) &&
                        (binding.etEmail.editText?.text?.length!! > 1 && !binding.etEmail.isErrorEnabled) &&
                        (binding.etPassword.editText?.text?.length!! > 1 && !binding.etPassword.isErrorEnabled) && b
        }
    }

    private fun onRegister() {
        val email = binding.etEmail.editText?.text
        val username = binding.etUsername.editText?.text
        val password = binding.etPassword.editText?.text
        val fullname = binding.etName.editText?.text
        val userType = intent.getStringExtra(SelectUserActivity.USER_TYPE)
        val role = if (userType == "pencari") {
            "penyewa"
        } else {
            "pemilik"
        }
        binding.btnRegister.setOnClickListener {
            registerViewModel.registerAccount(
                email.toString(),
                username.toString(),
                password.toString(),
                fullname.toString(),
                role
            ).observe(this@RegisterActivity) { result ->
                when (result.status) {
                    Status.LOADING -> {
                        binding.scrollView.setScrolling(false)
                        binding.pbLoading.layoutLoading.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.scrollView.setScrolling(true)
//                        Toast.makeText(this,
//                            "${result.data!!.data}",
//                            Toast.LENGTH_SHORT).show()
                        Toast.makeText(this,
                            "Daftar Berhasil",
                            Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, VerificationActivity::class.java)
                        finish()
                        startActivity(intent)
                        binding.pbLoading.layoutLoading.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        binding.scrollView.setScrolling(true)
                        if (result.message!!.contains("email")) {
                            binding.etEmail.error = result.message
                        } else if (result.message.contains("password")) {
                            binding.etPassword.error = result.message
                        } else if (result.message.contains("username")) {
                            binding.etUsername.error = result.message
                        } else if (result.message.contains("fullname")) {
                            binding.etName.error = result.message
                        } else {
                            Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
                        }
                        binding.pbLoading.layoutLoading.visibility = View.GONE
                    }
                }
            }
        }
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