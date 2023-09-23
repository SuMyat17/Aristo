package com.aristo.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R
import com.aristo.model.Category

class MainCategoriesListRecyclerViewAdapter(val context : Context, val mainCategoryList : ArrayList<Category>) : RecyclerView.Adapter<MainCategoriesListRecyclerViewAdapter.MainCategoriesListRecyclerViewHolder>() {

    private var selectedPosition = 0
    class MainCategoriesListRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val mainCatTitle = itemView.findViewById<TextView>(R.id.tvCatTitle)
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
        holder.mainCatTitle.setText(mainCategoryList[position].title)

        if (position == selectedPosition) {
            holder.itemView.setBackgroundResource(R.color.selectedCategories)
        } else {
            holder.itemView.setBackgroundResource(R.color.background)
        }

        holder.itemView.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            selectedPosition = holder.adapterPosition

            notifyItemChanged(previousSelectedPosition)

            notifyItemChanged(selectedPosition)

            (context as? MainCategoriesRecyclerViewListener)?.reloadSubCategoriesRecyclerView(holder.adapterPosition)
        }
    }
}