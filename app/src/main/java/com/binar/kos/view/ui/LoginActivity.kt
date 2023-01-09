package com.binar.kos.view.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.binar.kos.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.isEnabled = false
        checkButton()
    }

    private fun checkButton(){
        binding.etEmail.editText?.doOnTextChanged { _, _, _, _ ->
            if(!binding.etEmail.isErrorEnabled){
                binding.btnLogin.isEnabled = (binding.etEmail.editText?.text.toString().isNotEmpty())
            }else{
                binding.btnLogin.isEnabled = false
            }
        }
        binding.etPassword.editText?.doOnTextChanged { _, _, _, _ ->
            if(!binding.etPassword.isErrorEnabled){
                binding.btnLogin.isEnabled = (binding.etPassword.editText?.text.toString().isNotEmpty())
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


}

