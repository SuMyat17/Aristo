package com.aristo.Manager

fun processColorCode(apiColorCode: String): String {
    // Check if apiColorCode starts with "#" and add "#" if it doesn't
    val processedColorCode = if (!apiColorCode.startsWith("#")) {
        "#$apiColorCode"
    } else {
        apiColorCode
    }

    return processedColorCode
}