package com.aristo.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R
import com.aristo.view.viewHolders.CartViewHolder

class CartAdapter : RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_cart, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 5
    }

}