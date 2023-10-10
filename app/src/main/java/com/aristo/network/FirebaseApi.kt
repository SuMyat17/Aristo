package com.aristo.network

import android.util.Log
import com.aristo.admin.model.Category
import com.aristo.data.AdminDataHolder
import com.aristo.data.CategoryDataHolder
import com.aristo.model.Admin
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

    fun getAdmin(completionHandler: (Boolean, Any?) -> Unit) {

        database.reference.child("companyInfo").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val admin = snapshot.getValue(Admin::class.java)
                AdminDataHolder.instance.admin = admin as Admin
                completionHandler(true, admin)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("asfdasf", "cancelled")
                completionHandler(false, error.message)
            }

        })
    }

}