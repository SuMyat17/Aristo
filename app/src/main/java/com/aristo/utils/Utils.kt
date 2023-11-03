package com.aristo.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import com.aristo.R
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

fun processColorCode(apiColorCode: String): String {
    // Check if apiColorCode starts with "#" and add "#" if it doesn't
    val processedColorCode = if (!apiColorCode.startsWith("#")) {
        "#$apiColorCode"
    } else {
        apiColorCode
    }

    return processedColorCode
}
