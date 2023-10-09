package com.aristo.model

import java.io.Serializable

data class ShopDetail(
    var address: String = "",
    val companyName: String ="",
    val fbPage: String ="",
    var image: String = "",
    val phone: String = "",
    val viber: String = "",
): Serializable {
}