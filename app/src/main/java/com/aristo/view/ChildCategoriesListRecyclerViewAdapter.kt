package com.aristo.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R
import com.aristo.admin.model.Category

class ChildCategoriesListRecyclerViewAdapter(val context: Context, val childCategoryList : ArrayList<Category>) : RecyclerView.Adapter<ChildCategoriesListRecyclerViewAdapter.ChildCategoriesListRecyclerViewHolder>() {

    class ChildCategoriesListRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val subCatTitle = itemView.findViewById<TextView>(R.id.tvCatTitle)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChildCategoriesListRecyclerViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.sub_cat_items, parent, false)
        return ChildCategoriesListRecyclerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return childCategoryList.size
    }

    override fun onBindViewHolder(holder: ChildCategoriesListRecyclerViewHolder, position: Int) {

        holder.subCatTitle.setText(childCategoryList[position].title)

        holder.itemView.setOnClickListener {

            //Toast.makeText(context,"Child Categories List ${childCategoryList[position].childCategories}", Toast.LENGTH_LONG).show()


            Toast.makeText(context,"Is Empty ${childCategoryList[position].subCategories.isEmpty()}", Toast.LENGTH_LONG).show()


            if (childCategoryList[position].subCategories.isEmpty()){
                val intent = Intent(context, ProductListActivity:: class.java)
                //intent.putExtra("productList", childCategoryList[position].subCategories)
                context.startActivity(intent)
            }
            else{
                val intent = Intent(context, ChildCategoriesActivity:: class.java)
                //intent.putExtra("childCategoriesList", childCategoryList[position].subCategories)
                context.startActivity(intent)
            }


        }

    }

}