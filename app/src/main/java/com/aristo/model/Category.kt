package com.aristo.model

import java.io.Serializable

data class Category(
    val id: Int,
    val title: String,
    val imageURL : String,
    val productList: ArrayList<Product>,
    val childCategories : ArrayList<Category>
): Serializable{
}

data class Product(
    val id : Int,
    val productName: String,
    val imageURL : String,
    val totalCount : Int,
    val isNew : Boolean
): Serializable {
}