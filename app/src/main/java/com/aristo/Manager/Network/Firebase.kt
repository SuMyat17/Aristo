package com.aristo.Manager.Network

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import com.aristo.admin.model.Category
import com.aristo.model.ShopDetail
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.net.URI

class Firebase {

    companion object{

        val database = FirebaseDatabase.getInstance()

        fun getShopDetail(activity: Activity,completionHandler: (Boolean, ShopDetail?) -> Unit){

            val detailRef: DatabaseReference = database.getReference("companyInfo")

            detailRef.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    val shopDetail = snapshot.getValue(ShopDetail::class.java)

                    completionHandler(true,shopDetail)
                }

                override fun onCancelled(error: DatabaseError) {

                    completionHandler(false,null)
                }

            })
        }

    }
}