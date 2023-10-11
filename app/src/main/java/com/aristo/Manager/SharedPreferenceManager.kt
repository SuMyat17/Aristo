package com.aristo.Manager

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import com.aristo.model.Cart
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object SharedPreferenceManager {
    private lateinit var sharedPreferences: SharedPreferences

    fun initializeSharedPref(context: Context, name: String) {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    fun saveCartList(cartList: List<Cart>) {
        val editor = sharedPreferences.edit()
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(cartList)
        val serializedObject =
            Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
        editor.putString("cartList", serializedObject)
        editor.apply()
    }

    fun getCartList(): List<Cart> {
        val serializedObject = sharedPreferences.getString("cartList", null)

        if (serializedObject != null) {
            val byteArray = Base64.decode(serializedObject, Base64.DEFAULT)
            val byteArrayInputStream = ByteArrayInputStream(byteArray)
            val objectInputStream = ObjectInputStream(byteArrayInputStream)
            return objectInputStream.readObject() as? List<Cart> ?: emptyList()
        }
        return emptyList()
    }
}