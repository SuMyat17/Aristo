package com.aristo.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R
import com.aristo.view.viewHolders.NewItemViewHolder

class NewItemsAdapter : RecyclerView.Adapter<NewItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_new_items, parent, false)
        return NewItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewItemViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 5
    }

}