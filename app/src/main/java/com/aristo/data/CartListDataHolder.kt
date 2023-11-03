package com.aristo.data

import android.net.Uri
import com.aristo.model.Cart

class CartListDataHolder private constructor(){

    var cartList : ArrayList<Cart> = arrayListOf()

    companion object {
        val instance: CartListDataHolder = CartListDataHolder()
    }

}