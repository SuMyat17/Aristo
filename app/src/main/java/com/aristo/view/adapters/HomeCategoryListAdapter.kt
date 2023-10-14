package com.aristo.view.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aristo.utils.processColorCode
import com.aristo.admin.model.Category
import com.aristo.databinding.ViewHolderCategoryListBinding
import com.bumptech.glide.Glide

class HomeCategoryListAdapter(private val listener: HomeMainCategoryListener) : RecyclerView.Adapter<HomeCategoryListAdapter.HomeCategoryListViewHolder>() {

    private var dataList: List<Category> = listOf()

    class HomeCategoryListViewHolder(private val binding: ViewHolderCategoryListBinding, val context: Context, val listener: HomeMainCategoryListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category, position: Int) {
            itemView.setOnClickListener {
                listener.onTapHomeMainCategory(position)
            }

            binding.tvFirstCategory.text = category.title

            if(category.colorCode != ""){
                binding.ivFirstCategory.foreground = ColorDrawable(Color.parseColor(processColorCode(category.colorCode)))
            }
            else{
                Glide.with(context).load(category.imageURL).into(binding.ivFirstCategory)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryListViewHolder {
        val binding = ViewHolderCategoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeCategoryListViewHolder(binding, parent.context, listener)
    }

    override fun onBindViewHolder(holder: HomeCategoryListViewHolder, position: Int) {
        if (dataList.isNotEmpty()) {
            holder.bind(dataList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setNewData(categoryList: List<Category>) {
        dataList = categoryList
        notifyDataSetChanged()
    }

    interface HomeMainCategoryListener {
        fun onTapHomeMainCategory(position: Int)
    }
}