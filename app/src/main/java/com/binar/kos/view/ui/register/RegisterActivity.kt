package com.binar.kos.view.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.binar.kos.R
import com.binar.kos.databinding.ActivityRegisterBinding
import com.binar.kos.view.ui.selectUser.SelectUserActivity
import com.binar.kos.view.ui.login.LoginActivity
import com.binar.kos.view.ui.verification.VerificationActivity


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userType = intent.getStringExtra(SelectUserActivity.USER_TYPE)

        checkUserType(userType!!)

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, VerificationActivity::class.java)
            finish()
            startActivity(intent)
        }

        binding.tvBtnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra(SelectUserActivity.USER_TYPE,userType)
            finish()
            startActivity(intent)
        }
    }

    private fun checkUserType(userType: String){
        if(userType == "pencari"){
            binding.ivLogin1.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.image_login_1, null))
        }else if(userType == "penyewa"){
            binding.ivLogin1.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.image_login_2, null))
        }
    }

}