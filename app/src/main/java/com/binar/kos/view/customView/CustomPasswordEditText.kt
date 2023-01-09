package com.binar.kos.view.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.binar.kos.R


class CustomPasswordEditText : TextInputLayout {

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
        errorIconDrawable = null
        endIconMode =  END_ICON_PASSWORD_TOGGLE
        endIconDrawable = ContextCompat.getDrawable(context, R.drawable.show_password_selector) as Drawable
        editText?.doOnTextChanged { inputText, _, _, _ ->
            if(inputText?.length!! < 1){
                error = "Kamu belum isi password-mu"
            } else {
                error = null
                isErrorEnabled = false
            }
        }
    }
    private fun init() {
    }
}