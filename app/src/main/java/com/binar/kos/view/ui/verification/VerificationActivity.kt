package com.binar.kos.view.ui.verification

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Rect
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.binar.kos.R
import com.binar.kos.databinding.ActivityVerificationBinding
import com.binar.kos.databinding.SentDialogBinding
import com.binar.kos.utils.Status
import com.binar.kos.utils.hideLoading
import com.binar.kos.utils.showLoading
import com.binar.kos.view.ui.selectUser.SelectUserActivity
import com.binar.kos.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class VerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationBinding
    lateinit var cTimer: CountDownTimer
    private lateinit var isdialog: AlertDialog
    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnVerif.isEnabled = false
        val email = intent.getStringExtra("email")
        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")
        val fullname = intent.getStringExtra("fullname")
        val role = intent.getStringExtra("role")
        attachTextWatchers()
        onVerif()
        otpConfig()
        checkButton()
        setTimer()
        sendOtp(email,username,password,fullname,role)
    }


    private fun sendOtp(email : String?, username: String?, password: String?, fullname: String?, role: String?){
        binding.btnSendOtp.setOnClickListener {
            cTimer.start()
            registerViewModel.resendOtp(
                email!!,username!!,password!!,fullname!!,role!!
            ).observe(this@VerificationActivity) { result ->
                when (result.status) {
                    Status.LOADING -> {
                        showLoading(this)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        Toast.makeText(this,
                            "Kirim kode OTP berhasil",
                            Toast.LENGTH_SHORT).show()
                    }
                    Status.ERROR -> {
                        hideLoading()
                        Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setTimer() {
        cTimer = object : CountDownTimer(180 * 1000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(l: Long) {
                binding.btnSendOtp.isVisible = false

                binding.tvTitleTimer.isVisible = true
                binding.tvTimer.isVisible = true
                val value = l + 1000
                val minutes = ((value / 1000) / 60)
                var textminute = minutes.toString()
                val seconds = ((value / 1000) % 60)
                var textsecond = seconds.toString()
                if (minutes < 10) {
                     textminute = "0$minutes"
                }
                if(seconds < 10){
                    textsecond = "0$seconds"
                }
                binding.tvTimer.text = "$textminute:$textsecond"
            }

            override fun onFinish() {
                binding.tvTitleTimer.isVisible = false
                binding.tvTimer.isVisible = false
                binding.btnSendOtp.isVisible = true
            }
        }
        cTimer.start()
    }

    private fun otpConfig() {
        binding.etOtp1.editText!!.doOnTextChanged { _, _, _, _ ->
            checkButton()
        }
        binding.etOtp2.editText!!.doOnTextChanged { _, _, _, _ ->
            checkButton()
        }
        binding.etOtp3.editText!!.doOnTextChanged { _, _, _, _ ->
            checkButton()
        }
        binding.etOtp4.editText!!.doOnTextChanged { _, _, _, _ ->
            checkButton()
        }
        binding.etOtp5.editText!!.doOnTextChanged { _, _, _, _ ->
            checkButton()
        }
        binding.etOtp6.editText!!.doOnTextChanged { _, _, _, _ ->
            checkButton()
        }
        binding.etOtp6.editText!!.doOnTextChanged { _, _, _, _ ->
            checkButton()
        }
    }

    private fun checkButton() {
        if (binding.etOtp1.editText!!.text.isNotEmpty() && binding.etOtp2.editText!!.text.isNotEmpty()
            && binding.etOtp3.editText!!.text.isNotEmpty() && binding.etOtp4.editText!!.text.isNotEmpty()
            && binding.etOtp5.editText!!.text.isNotEmpty() && binding.etOtp6.editText!!.text.isNotEmpty()
        ) {
            binding.btnVerif.isEnabled = true
        }
    }

    private fun onVerif() {
        binding.btnVerif.setOnClickListener {
            registerViewModel.verifAccount(
                "${binding.etOtp1.editText!!.text}${binding.etOtp2.editText!!.text}" +
                        "${binding.etOtp3.editText!!.text}${binding.etOtp4.editText!!.text}" +
                        "${binding.etOtp5.editText!!.text}${binding.etOtp6.editText!!.text}"
            ).observe(this@VerificationActivity) { result ->
                when (result.status) {
                    Status.LOADING -> {
                        showLoading(this)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        val binding: SentDialogBinding =
                            SentDialogBinding.inflate(LayoutInflater.from(this))
                        val builder = AlertDialog.Builder(this)
                        builder.setView(binding.root)
                        builder.setCancelable(false)
                        binding.btnClose.setOnClickListener {
                            val sentBinding: SentDialogBinding =
                                SentDialogBinding.inflate(LayoutInflater.from(this))
                            val builderDialog = AlertDialog.Builder(this)
                            builderDialog.setView(sentBinding.root)
                            builderDialog.setCancelable(false)
                            sentBinding.btnClose.setOnClickListener {
                                isdialog.dismiss()
                                startActivity(intent)
                            }
                            isdialog = builderDialog.create()
                            isdialog.show()
                        }
                        isdialog = builder.create()
                        isdialog.show()

                    }
                    Status.ERROR -> {
                        hideLoading()
                            Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun attachTextWatchers() {
        binding.etOtp1.editText!!.addTextChangedListener(GenericTextWatcher(binding.etOtp1.editText!!,
            binding.etOtp2.editText!!))
        binding.etOtp2.editText!!.addTextChangedListener(GenericTextWatcher(binding.etOtp2.editText!!,
            binding.etOtp3.editText!!))
        binding.etOtp3.editText!!.addTextChangedListener(GenericTextWatcher(binding.etOtp3.editText!!,
            binding.etOtp4.editText!!))
        binding.etOtp4.editText!!.addTextChangedListener(GenericTextWatcher(binding.etOtp4.editText!!,
            binding.etOtp5.editText!!))
        binding.etOtp5.editText!!.addTextChangedListener(GenericTextWatcher(binding.etOtp5.editText!!,
            binding.etOtp6.editText!!))
        binding.etOtp6.editText!!.addTextChangedListener(GenericTextWatcher(binding.etOtp6.editText!!,
            null))
        binding.etOtp2.editText!!.setOnKeyListener(GenericKeyEvent(binding.etOtp2.editText!!,
            binding.etOtp1.editText!!))
        binding.etOtp3.editText!!.setOnKeyListener(GenericKeyEvent(binding.etOtp3.editText!!,
            binding.etOtp2.editText!!))
        binding.etOtp4.editText!!.setOnKeyListener(GenericKeyEvent(binding.etOtp4.editText!!,
            binding.etOtp3.editText!!))
        binding.etOtp5.editText!!.setOnKeyListener(GenericKeyEvent(binding.etOtp5.editText!!,
            binding.etOtp4.editText!!))
        binding.etOtp6.editText!!.setOnKeyListener(GenericKeyEvent(binding.etOtp6.editText!!,
            binding.etOtp5.editText!!))
    }

    private class GenericTextWatcher(
        private val currentView: EditText,
        nextView: EditText?,
    ) :
        TextWatcher {
        private val nextView: EditText?

        init {
            this.nextView = nextView
        }

        override fun afterTextChanged(editable: Editable) {
            // TODO Auto-generated method stub
            val text = editable.toString()
            if (nextView != null && text.length == 1) {
                nextView.requestFocus()
            }
            if (text.length > 1) {
                currentView.setText(text[text.length - 1].toString())
                currentView.setSelection(1)
            }
        }

        override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
            // TODO Auto-generated method stub
        }

        override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
            // TODO Auto-generated method stub
        }
    }


    class GenericKeyEvent(
        private val currentView: EditText,
        private val previousView: EditText?,
    ) :
        View.OnKeyListener {
        override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
            @Suppress("DEPRECATED_IDENTITY_EQUALS")
            if (event.action === KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.text.toString()
                    .isEmpty()
            ) {
                previousView?.requestFocus()
                return true
            }
            return false
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