package com.aristo.model

import java.io.Serializable

data class Category(
    val id: Int,
    val title: String,
    val subCategories : ArrayList<SubCategories>
): Serializable{
}

data class SubCategories(
    val id: Int,
    val title: String,
    val childCategories : ArrayList<ChildCategories>
): Serializable{
}

data class ChildCategories(
    val id: Int,
    val title: String,
    val productList: ArrayList<Product>,
    val childCategories : ArrayList<ChildCategories>
): Serializable{
}

data class Product(
    val id : Int,
    val name: String
): Serializable {
}