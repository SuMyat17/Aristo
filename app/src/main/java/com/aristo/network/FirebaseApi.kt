package com.aristo.network

import com.aristo.admin.model.Category
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseApi {

    val database = FirebaseDatabase.getInstance()
    val categoriesRef: DatabaseReference = database.getReference("Products")

    fun getMainCategoryData(completionHandler: (Boolean, ArrayList<Category>?) -> Unit) {

        categoriesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val categoriesSnapshot = snapshot.child("Categories")
                val categoriesList: ArrayList<Category> = ArrayList()

                for (categorySnapshot in categoriesSnapshot.children) {

                    val categoryList = categorySnapshot.getValue(Category::class.java)
                    categoryList?.let {
                        categoriesList.add(it)
                    }
                }
                completionHandler(true, categoriesList)
            }

            override fun onCancelled(error: DatabaseError) {
                completionHandler(false, null)
            }

        })
    }

}