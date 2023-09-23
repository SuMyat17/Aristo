package com.aristo.view.viewHolders

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.aristo.view.ProductDetailActivity

class NewItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener {
            itemView.context.startActivity(Intent(itemView.context, ProductDetailActivity::class.java))
        }
    }
}