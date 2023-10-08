package com.aristo.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R
import com.aristo.admin.model.Category

class SubCategoriesListRecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<SubCategoriesListRecyclerViewAdapter.SubCategoriesListRecyclerViewHolder>() {

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
        return 4
    }

    override fun onBindViewHolder(holder: SubCategoriesListRecyclerViewHolder, position: Int) {

        //holder.subCatTitle.setText(subCategoryList[position].title)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChildCategoriesActivity:: class.java)

            // Put the ArrayList as an extra in the intent
            //intent.putExtra("childCategoriesList", subCategoryList[position].subCategories)

            context.startActivity(intent)
        }

    }

}