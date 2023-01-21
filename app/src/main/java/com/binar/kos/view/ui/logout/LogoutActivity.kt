package com.binar.kos.view.ui.login

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import com.binar.kos.R
import com.binar.kos.databinding.ActivityLogoutBinding
import com.binar.kos.utils.Status
import com.binar.kos.view.ui.home.HomeActivity
import com.binar.kos.view.ui.register.RegisterActivity
import com.binar.kos.view.ui.selectUser.SelectUserActivity
import com.binar.kos.view.ui.verification.VerificationActivity
import com.binar.kos.viewmodel.LoginViewModel
import com.binar.kos.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class LogoutActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLogoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

