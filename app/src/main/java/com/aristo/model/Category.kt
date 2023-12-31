package com.aristo.model

import java.io.Serializable

data class Category(
    var id: String = "",
    val title: String ="",
    val price: Int = 0,
    var imageURL: String = "",
    val new: Boolean = false,
    val colorCode : String = "",
    val type : String = "",
    val timeStamp: Long = 0L,
    //var subCategories: ArrayList<Category> = ArrayList()
    var subCategories: Map<String, Category> = mapOf()
): Serializable{
}

