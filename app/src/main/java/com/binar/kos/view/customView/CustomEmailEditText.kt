package com.binar.kos.view.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.binar.kos.R
import com.google.android.material.textfield.TextInputLayout

class CustomEmailEditText : TextInputLayout {

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        editText?.hint= "Email"
        editText?.doOnTextChanged { inputText, _, _, _ ->
            if (inputText?.length!! < 1) {
                error = "Kamu belum isi email-mu"
            }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(inputText!!).matches()){
                error =  "Tolong isi alamat email yang benar"
            }else{
                error = null
                isErrorEnabled = false
                endIconMode =  END_ICON_CLEAR_TEXT
                endIconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_outline_cancel_24) as Drawable
            }
        }
    }
    private fun init() {
    }
}