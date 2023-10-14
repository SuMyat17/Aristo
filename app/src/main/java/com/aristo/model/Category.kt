package com.aristo.admin.model

import android.net.Uri
import java.io.Serializable

data class Category(
    var id: String = "",
    val title: String ="",
    val price: Int = 0,
    var imageURL: String = "",
    val new: Boolean = false,
    val colorCode : String = "",
    val type : String = "",
    //var subCategories: ArrayList<Category> = ArrayList()
    var subCategories: Map<String, Category> = mapOf()
): Serializable{
}

