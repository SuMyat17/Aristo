package com.aristo.utils

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
    } else if (modifiedInput.startsWith("+959")) {
        modifiedInput.replaceFirst("^\\+959".toRegex(), "959")
    } else if (modifiedInput.startsWith("959")) {
        modifiedInput
    } else {
        "959$modifiedInput"
    }

    return modifiedInput
}