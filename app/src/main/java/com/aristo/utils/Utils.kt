package com.aristo.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import com.aristo.R
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.regex.Pattern

fun processColorCode(apiColorCode: String): String {
    // Check if apiColorCode starts with "#" and add "#" if it doesn't
    val processedColorCode = if (!apiColorCode.startsWith("#")) {
        "#$apiColorCode"
    } else {
        apiColorCode
    }

    return processedColorCode
}

fun modifyPhoneNumber(phoneNumber: String): String {
    var modifiedInput = phoneNumber

    modifiedInput = if (modifiedInput.startsWith("09")){
        modifiedInput.replaceFirst("^09".toRegex(), "959")
    }
    else if (modifiedInput.startsWith("+959")) {
        modifiedInput.replaceFirst("^\\+959".toRegex(), "959")
    } else {
        "959$modifiedInput"
    }

    return modifiedInput
}