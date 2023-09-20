package com.aristo.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R
import com.aristo.model.Category

class MainCategoriesListRecyclerViewAdapter(val context : Context, val mainCategoryList : ArrayList<Category>) : RecyclerView.Adapter<MainCategoriesListRecyclerViewAdapter.MainCategoriesListRecyclerViewHolder>() {

    class MainCategoriesListRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainCategoriesListRecyclerViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.main_cat_items, parent, false)
        return MainCategoriesListRecyclerViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return mainCategoryList.size
    }

    override fun onBindViewHolder(holder: MainCategoriesListRecyclerViewHolder, position: Int) {

    }
}