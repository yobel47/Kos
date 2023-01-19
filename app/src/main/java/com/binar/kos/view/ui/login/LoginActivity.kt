package com.binar.kos.view.ui.login

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import com.binar.kos.R
import com.binar.kos.databinding.ActivityLoginBinding
import com.binar.kos.utils.Status
import com.binar.kos.view.ui.home.HomeActivity
import com.binar.kos.view.ui.register.RegisterActivity
import com.binar.kos.view.ui.selectUser.SelectUserActivity
import com.binar.kos.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.isEnabled = false
        val userType = intent.getStringExtra(SelectUserActivity.USER_TYPE)

        checkUserType(userType!!)
        checkButton()
        onLogin()

        binding.tvBtnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra(SelectUserActivity.USER_TYPE,userType)
            finish()
            startActivity(intent)
        }
    }

    private fun checkUserType(userType: String){
        if(userType == "pencari"){
            binding.ivLogin1.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.image_login_1, null))
            binding.tvWelcomeDescription.text = resources.getString(R.string.tolong_isi_data_dirimu_untuk_masuk_sebagai_pencari_kost)
        }else if(userType == "penyewa"){
            binding.ivLogin1.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.image_login_2, null))
            binding.tvWelcomeDescription.text = resources.getString(R.string.tolong_isi_data_dirimu_untuk_masuk_sebagai_penyewa_kost)
        }
    }

    private fun checkButton(){
        binding.etEmail.editText?.doOnTextChanged { _, _, _, _ ->
            if(!binding.etEmail.isErrorEnabled){
                binding.btnLogin.isEnabled = (binding.etEmail.editText?.text.toString().isNotEmpty() && binding.etPassword.editText?.text.toString().isNotEmpty())
            }else{
                binding.btnLogin.isEnabled = false
            }
        }
        binding.etPassword.editText?.doOnTextChanged { _, _, _, _ ->
            if(!binding.etPassword.isErrorEnabled){
                binding.btnLogin.isEnabled = (binding.etPassword.editText?.text.toString().isNotEmpty() && binding.etEmail.editText?.text.toString().isNotEmpty() )
            }else{
                binding.btnLogin.isEnabled = false
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

    private fun onLogin() {
        val email = binding.etEmail.editText?.text
        val password = binding.etPassword.editText?.text

        if (email != null && password != null) {
            binding.btnLogin.setOnClickListener {
                loginViewModel.loginAccount(email.toString(), password.toString())
                    .observe(this) { result ->
                        when (result.status) {
                            Status.LOADING -> {}
                            Status.SUCCESS -> {
                                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_LONG).show()
                                val intent = Intent(this, HomeActivity::class.java)
                                finish()
                                startActivity(intent)
                            }
                            Status.ERROR -> {
                                Toast.makeText(this, "Login Error!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }
        }


    }

}

