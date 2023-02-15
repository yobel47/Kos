package com.binar.kos.view.ui.login

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.binar.kos.databinding.ActivityLoginBinding
import com.binar.kos.utils.Status
import com.binar.kos.utils.getFirebaseToken
import com.binar.kos.utils.hideLoading
import com.binar.kos.utils.showLoading
import com.binar.kos.view.ui.home.HomeActivity
import com.binar.kos.view.ui.homePenyewa.HomePenyewaActivity
import com.binar.kos.view.ui.selectUser.SelectUserActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.LoginViewModel
import com.binar.kos.viewmodel.MainViewModel
import okhttp3.MultipartBody
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()
    private val dataStore: DatastoreViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.isEnabled = false

        checkButton()
        onLogin()

        getFirebaseToken()

        binding.tvBtnRegister.setOnClickListener {
            val intent = Intent(this, SelectUserActivity::class.java)
            finish()
            startActivity(intent)
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        @Suppress("DEPRECATION")
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        finish()
        startActivity(intent)
    }


    private fun onLogin() {
        binding.btnLogin.setOnClickListener {
            loginViewModel.loginAccount(binding.etEmail.editText!!.text.toString(),
                binding.etPassword.editText!!.text.toString())
                .observe(this@LoginActivity) { result ->
                    when (result.status) {
                        Status.LOADING -> {
                            binding.scrollView.setScrolling(false)
                            showLoading(this)
                        }
                        Status.SUCCESS -> {
                            mainViewModel.subscribeToken(
                                result.data!!.accessToken!!,
                                MultipartBody.Builder().setType(MultipartBody.FORM)
                                    .addFormDataPart("fcm_token",
                                        getFirebaseToken()).build()
                            ).observe(this@LoginActivity) { resultSubscribe ->
                                when (resultSubscribe.status) {
                                    Status.LOADING -> {
                                        binding.scrollView.setScrolling(false)
                                    }
                                    Status.SUCCESS -> {
                                        hideLoading()
                                        binding.scrollView.setScrolling(true)
                                        Toast.makeText(this,
                                            "Berhasil Login",
                                            Toast.LENGTH_SHORT).show()
                                        if (result.data.role == "ROLE_PENCARI") {
                                            val intent = Intent(this, HomeActivity::class.java)
                                            dataStore.saveLoginState(true)
                                            dataStore.saveAccessToken(result.data.accessToken!!)
                                            dataStore.saveRole(result.data.role)
                                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                            finishAffinity()
                                            startActivity(intent)
                                        } else {
                                            val intent =
                                                Intent(this, HomePenyewaActivity::class.java)
                                            dataStore.saveLoginState(true)
                                            dataStore.saveAccessToken(result.data.accessToken!!)
                                            dataStore.saveRole(result.data.role!!)
                                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                            finishAffinity()
                                            startActivity(intent)
                                        }

                                    }
                                    Status.ERROR -> {
                                        Toast.makeText(this,
                                            "${resultSubscribe.message}",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                        Status.ERROR -> {
                            hideLoading()
                            binding.scrollView.setScrolling(true)
                            if (result.message!!.contains("email")) {
                                binding.etEmail.error = result.message
                                binding.btnLogin.isEnabled = false
                            } else if (result.message.contains("password")) {
                                binding.etPassword.error = result.message
                                binding.btnLogin.isEnabled = false
                            } else {
                                Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
        }
    }

    private fun checkButton() {
        binding.etEmail.editText?.onFocusChangeListener =
            OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    if (binding.etEmail.editText?.text!!.isEmpty()) {
                        binding.etEmail.error = "Kamu belum isi email-mu"
                    }
                }
            }

        binding.etPassword.editText?.onFocusChangeListener =
            OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    if (binding.etPassword.editText?.text!!.isEmpty()) {
                        binding.etPassword.error = "Kamu belum isi password-mu"
                    }
                }
            }

        binding.etPassword.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (!binding.etEmail.isErrorEnabled && !binding.etPassword.isErrorEnabled) {
                    binding.btnLogin.isEnabled =
                        (binding.etEmail.editText?.text?.length!! > 1 && !binding.etEmail.isErrorEnabled) &&
                                (binding.etPassword.editText?.text?.length!! > 1 && !binding.etPassword.isErrorEnabled)
                } else {
                    binding.btnLogin.isEnabled = false
                }
            }
        })
        binding.etEmail.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (!binding.etEmail.isErrorEnabled && !binding.etPassword.isErrorEnabled) {
                    binding.btnLogin.isEnabled =
                        (binding.etEmail.editText?.text?.length!! > 1 && !binding.etEmail.isErrorEnabled) &&
                                (binding.etPassword.editText?.text?.length!! > 1 && !binding.etPassword.isErrorEnabled)
                } else {
                    binding.btnLogin.isEnabled = false
                }
            }
        })
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

