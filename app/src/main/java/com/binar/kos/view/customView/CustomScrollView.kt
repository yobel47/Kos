package com.binar.kos.view.customView

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView

class CustomScrollView : ScrollView {
    private var enableScrolling = true

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (scrollingEnabled()) {
            super.onInterceptTouchEvent(ev)
        } else {
            false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (scrollingEnabled()) {
            super.onTouchEvent(ev)
        } else {
            false
        }
    }

    private fun scrollingEnabled(): Boolean {
        return enableScrolling
    }

    fun setScrolling(enableScrolling: Boolean) {
        this.enableScrolling = enableScrolling
    }
}