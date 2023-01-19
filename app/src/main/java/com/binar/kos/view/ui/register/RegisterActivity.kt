package com.binar.kos.view.ui.register

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
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
        onRegister()

        binding.tvBtnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra(SelectUserActivity.USER_TYPE, userType)
            finish()
            startActivity(intent)
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
        binding.etEmail.editText?.doOnTextChanged { _, _, _, _ ->
            if (!binding.etEmail.isErrorEnabled) {
                binding.btnRegister.isEnabled = (binding.etEmail.editText?.text.toString()
                    .isNotEmpty() && binding.etPassword.editText?.text.toString()
                    .isNotEmpty() && binding.etName.editText?.text.toString()
                    .isNotEmpty() && binding.etUsername.editText?.text.toString()
                    .isNotEmpty() && binding.cbPolicy.isChecked)
            } else {
                binding.btnRegister.isEnabled = false
            }
        }
        binding.etPassword.editText?.doOnTextChanged { _, _, _, _ ->
            if (!binding.etPassword.isErrorEnabled) {
                binding.btnRegister.isEnabled = (binding.etEmail.editText?.text.toString()
                    .isNotEmpty() && binding.etPassword.editText?.text.toString()
                    .isNotEmpty() && binding.etName.editText?.text.toString()
                    .isNotEmpty() && binding.etUsername.editText?.text.toString()
                    .isNotEmpty() && binding.cbPolicy.isChecked)
            } else {
                binding.btnRegister.isEnabled = false
            }
        }
        binding.etName.editText?.doOnTextChanged { _, _, _, _ ->
            if (!binding.etName.isErrorEnabled) {
                binding.btnRegister.isEnabled = (binding.etEmail.editText?.text.toString()
                    .isNotEmpty() && binding.etPassword.editText?.text.toString()
                    .isNotEmpty() && binding.etName.editText?.text.toString()
                    .isNotEmpty() && binding.etUsername.editText?.text.toString()
                    .isNotEmpty() && binding.cbPolicy.isChecked)
            } else {
                binding.btnRegister.isEnabled = false
            }
        }
        binding.etUsername.editText?.doOnTextChanged { _, _, _, _ ->
            if (!binding.etUsername.isErrorEnabled) {
                binding.btnRegister.isEnabled = (binding.etEmail.editText?.text.toString()
                    .isNotEmpty() && binding.etPassword.editText?.text.toString()
                    .isNotEmpty() && binding.etName.editText?.text.toString()
                    .isNotEmpty() && binding.etUsername.editText?.text.toString()
                    .isNotEmpty() && binding.cbPolicy.isChecked)
            } else {
                binding.btnRegister.isEnabled = false
            }
        }
        binding.cbPolicy.setOnCheckedChangeListener { _, b ->
            if (b) {
                binding.btnRegister.isEnabled = (binding.etEmail.editText?.text.toString()
                    .isNotEmpty() && binding.etPassword.editText?.text.toString()
                    .isNotEmpty() && binding.etName.editText?.text.toString()
                    .isNotEmpty() && binding.etUsername.editText?.text.toString().isNotEmpty())
            }
        }
    }

    private fun onRegister() {
        val email = binding.etEmail.editText?.text
        val username = binding.etUsername.editText?.text
        val password = binding.etPassword.editText?.text
        val fullname = binding.etName.editText?.text
        binding.btnRegister.setOnClickListener {
            registerViewModel.registerAccount(email.toString(),
                username.toString(),
                password.toString(),
                fullname.toString()).observe(this@RegisterActivity) { result ->
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
                        Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
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