package com.aristo.data

import com.aristo.model.Cart

class CartListDataHolder private constructor(){

    var cartList : ArrayList<Cart>? = null

    companion object {
        val instance: CartListDataHolder = CartListDataHolder()
    }

}