package com.aristo.model

import java.io.Serializable

data class Cart (
    var product: Category? = null,
    var quantity: Int = 0,
): Serializable