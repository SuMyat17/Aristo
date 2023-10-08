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

class ProductListRecyclerViewAdapter(val context: Context, val productList : ArrayList<Category>) : RecyclerView.Adapter<ProductListRecyclerViewAdapter.ProductListRecyclerViewHolder>() {

    class ProductListRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val productTitle = itemView.findViewById<TextView>(R.id.tvCatTitle)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListRecyclerViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.sub_cat_items, parent, false)
        return ProductListRecyclerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductListRecyclerViewHolder, position: Int) {

        holder.productTitle.setText(productList[position].title)

        holder.itemView.setOnClickListener {

            Toast.makeText(context,"Product List ${productList[position].title}", Toast.LENGTH_LONG).show()

            val intent = Intent(context, ProductDetailActivity:: class.java)

            context.startActivity(intent)
        }

    }

}