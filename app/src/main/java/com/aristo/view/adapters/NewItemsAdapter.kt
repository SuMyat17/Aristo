package com.aristo.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aristo.admin.model.Category
import com.aristo.databinding.ViewHolderNewItemsBinding
import com.aristo.view.ProductDetailActivity
import com.bumptech.glide.Glide

class NewItemsAdapter(private var listener: NewItemListener) : RecyclerView.Adapter<NewItemsAdapter.NewItemViewHolder>() {

    private var dataList: List<Category> = listOf()

    class NewItemViewHolder(private val binding: ViewHolderNewItemsBinding, val context: Context, var listener: NewItemListener) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            itemView.setOnClickListener {
                listener.onTapNewItem(category)
            }

            binding.tvItemName.text = category.title
            Glide.with(context).load(category.imageURL).into(binding.ivFullImage)
            Glide.with(context).load(category.imageURL).into(binding.ivSmallImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewItemViewHolder {
        val binding = ViewHolderNewItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewItemViewHolder(binding, parent.context, listener)
    }

    override fun onBindViewHolder(holder: NewItemViewHolder, position: Int) {
        if (dataList.isNotEmpty()) {
            holder.bind(dataList[position])
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setNewData(categoryList: List<Category>) {
        dataList = categoryList
        notifyDataSetChanged()
    }

    interface NewItemListener {
        fun onTapNewItem(category: Category)
    }
}