package com.binar.kos.utils

import android.view.View
import com.binar.kos.data.local.entity.Filter

interface RecyclerViewClickListener {
    fun onItemClicked(view: View, filter: Filter)
}