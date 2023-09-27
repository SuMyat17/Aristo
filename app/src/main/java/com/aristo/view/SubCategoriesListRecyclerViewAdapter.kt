package com.aristo.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R
import com.aristo.model.Category

class SubCategoriesListRecyclerViewAdapter(val context: Context, val subCategoryList : ArrayList<Category>) : RecyclerView.Adapter<SubCategoriesListRecyclerViewAdapter.SubCategoriesListRecyclerViewHolder>() {

    class SubCategoriesListRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val subCatTitle = itemView.findViewById<TextView>(R.id.tvCatTitle)
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
        return subCategoryList.size
    }

    override fun onBindViewHolder(holder: SubCategoriesListRecyclerViewHolder, position: Int) {

        holder.subCatTitle.setText(subCategoryList[position].title)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChildCategoriesActivity:: class.java)

            // Put the ArrayList as an extra in the intent
            intent.putExtra("childCategoriesList", subCategoryList[position].childCategories)

            context.startActivity(intent)
        }

    }

}