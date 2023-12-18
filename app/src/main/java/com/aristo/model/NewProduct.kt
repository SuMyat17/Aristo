package com.aristo.model

data class NewProduct (
    var id: String = "",
    val timeStamp: Long = System.currentTimeMillis(),
    var productImage: String? = null,
)