package com.aristo.network

import com.aristo.model.Category
import com.aristo.model.Customer
import com.aristo.model.NewCategory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseApi {

    val database = FirebaseDatabase.getInstance()
    val categoriesRef: DatabaseReference = database.getReference("Products")
    val auth: FirebaseAuth = Firebase.auth

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


    fun getNewProducts(completionHandler: (Boolean, ArrayList<NewCategory>?) -> Unit) {

        categoriesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val categoriesSnapshot = snapshot.child("NewProducts")
                val newProductList: ArrayList<NewCategory> = ArrayList()

                for (categorySnapshot in categoriesSnapshot.children) {

                    val newProducts = categorySnapshot.getValue(NewCategory::class.java)
                    newProducts?.let {
                        newProductList.add(it)
                    }
                }
                completionHandler(true, newProductList)
            }

            override fun onCancelled(error: DatabaseError) {
                completionHandler(false, null)
            }

        })
    }

//    fun signInUser(completionHandler: (Boolean) -> Unit){
//
//        if (auth.currentUser == null){
//
//            var email = "tunlinaung.tla7@gmail.com"
//            var password = "Superst@r7"
//
//            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
//
//                if (task.isSuccessful){
//                    completionHandler(true)
//                }
//                else{
//                    //Toast.makeText(context,"${task.exception!!.localizedMessage.toString()}",Toast.LENGTH_LONG).show()
//                    completionHandler(false)
//                }
//            }
//        }
//    }

    fun signInUser(){

        if (auth.currentUser == null){

            var email = "tunlinaung.tla7@gmail.com"
            var password = "Superst@r7"

            auth.signInWithEmailAndPassword(email,password)
        }
    }

    fun signUpUser(customer: Customer, completionHandler: (Boolean) -> Unit) {
        database.reference.child("Users").child(customer.phone).setValue(customer)
            .addOnCompleteListener {
                if (it.isSuccessful) { completionHandler(true) }
                else { completionHandler(false) }
            }
    }

    fun updateUser(customer: Customer, completionHandler: (Boolean) -> Unit) {
        database.reference.child("Users").child(customer.phone).updateChildren(mapOf("userName" to customer.userName, "address" to customer.address)).addOnCompleteListener {
            if (it.isSuccessful) { completionHandler(true) }
            else { completionHandler(false) }
        }
    }

    fun getUser(phoneNumber: String, completionHandler: (String?, Customer?) -> Unit) {
        database.reference.child("Users").child(phoneNumber).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(Customer::class.java)
                    if (user != null) { completionHandler(null, user) }
                    else { completionHandler("Account does not exist", null) }
                } else { completionHandler("Account does not exist ", null) }
            }
            override fun onCancelled(error: DatabaseError) {
                completionHandler(error.message, null)
            }
        })
    }
}