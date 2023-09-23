package com.aristo.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R
import com.aristo.view.viewHolders.HomeCategoryListViewHolder

class HomeCategoryListAdapter : RecyclerView.Adapter<HomeCategoryListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_category_list, parent, false)
        return HomeCategoryListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeCategoryListViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

}