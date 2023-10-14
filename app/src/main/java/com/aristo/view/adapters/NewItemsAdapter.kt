package com.aristo.view.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aristo.utils.processColorCode
import com.aristo.admin.model.Category
import com.aristo.databinding.ViewHolderNewItemsBinding
import com.bumptech.glide.Glide

class NewItemsAdapter(private var listener: NewItemListener) : RecyclerView.Adapter<NewItemsAdapter.NewItemViewHolder>() {

    private var dataList: List<Category> = listOf()

    class NewItemViewHolder(private val binding: ViewHolderNewItemsBinding, val context: Context, var listener: NewItemListener) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            itemView.setOnClickListener {
                listener.onTapNewItem(category)
            }

            binding.tvItemName.text = category.title

            Log.d("bind Datas", "bind Datas: ${category.imageURL}")

            if (category.colorCode != "" && category.colorCode.count() in 7..10){
                binding.ivFullImage.foreground = ColorDrawable(Color.parseColor(processColorCode(category.colorCode)))
                binding.ivSmallImage.foreground = ColorDrawable(Color.parseColor(processColorCode(category.colorCode)))

            } else if (category.imageURL != "") {
                binding.ivFullImage.foreground = null
                binding.ivSmallImage.foreground = null
                Glide.with(context).load(category.imageURL).into(binding.ivFullImage)
                Glide.with(context).load(category.imageURL).into(binding.ivSmallImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewItemViewHolder {
        val binding = ViewHolderNewItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewItemViewHolder(binding, parent.context, listener)
    }

    override fun onBindViewHolder(holder: NewItemViewHolder, position: Int) {

        if (dataList.isNotEmpty()) {
            val actualPosition = if (itemCount > 0) position % itemCount else 0
            holder.bind(dataList[actualPosition])
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