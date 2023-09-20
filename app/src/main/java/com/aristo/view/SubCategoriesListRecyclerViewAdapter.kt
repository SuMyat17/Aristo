package com.aristo.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R

class SubCategoriesListRecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<SubCategoriesListRecyclerViewAdapter.SubCategoriesListRecyclerViewHolder>() {

    class SubCategoriesListRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubCategoriesListRecyclerViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.sub_cat_items, parent, false)
        return SubCategoriesListRecyclerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder: SubCategoriesListRecyclerViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            val intent = Intent(context, SubCategoriesActivity:: class.java)
            context.startActivity(intent)
        }

    }

}