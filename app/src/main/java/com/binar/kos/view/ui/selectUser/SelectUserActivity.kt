package com.binar.kos.view.ui.selectUser

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.binar.kos.databinding.ActivitySelectUserBinding
import com.binar.kos.databinding.SentDialogBinding
import com.binar.kos.view.ui.login.LoginActivity
import com.binar.kos.view.ui.register.RegisterActivity
import com.binar.kos.view.ui.verification.VerificationActivity

class SelectUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySelectUserBinding
    private lateinit var isdialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, RegisterActivity::class.java)
        binding.btnPencariKos.setOnClickListener {
            intent.putExtra(USER_TYPE,"pencari")
            startActivity(intent)
        }
        binding.btnPenyewaKos.setOnClickListener {
            intent.putExtra(USER_TYPE,"penyewa")
            startActivity(intent)
        }
    }

    companion object{
        const val USER_TYPE = "user_type"
    }
}