package com.aristo.view.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aristo.Manager.processColorCode
import com.aristo.R
import com.aristo.admin.model.Category
import com.aristo.databinding.ViewHolderMainCategoryBinding
import com.bumptech.glide.Glide

class MainCategoryListAdapter(private val context: Context, val listener: MainCategoriesRecyclerViewListener) : RecyclerView.Adapter<MainCategoryListAdapter.MainCategoryListViewHolder>() {

    private var selectedPosition = 0
    private var mainCategoryList = listOf<Category>()

    class MainCategoryListViewHolder(private var binding: ViewHolderMainCategoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(category: Category, context: Context, position: Int) {
            binding.tvFirstCategory.text = category.title

            if(category.colorCode != ""){
                binding.ivFirstCategory.foreground = ColorDrawable(Color.parseColor(processColorCode(category.colorCode)))
            }
            else{
                Glide.with(context).load(category.imageURL).into(binding.ivFirstCategory)
                binding.ivFirstCategory.foreground = null
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCategoryListViewHolder {
        val binding = ViewHolderMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainCategoryListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mainCategoryList.size
    }

    override fun onBindViewHolder(holder: MainCategoryListViewHolder, position: Int) {

        holder.bind(mainCategoryList[position], context,position)

        if (position == selectedPosition) {
            holder.itemView.setBackgroundResource(R.color.white)
        } else {
            holder.itemView.setBackgroundResource(R.color.color_primary)
        }

        holder.itemView.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            selectedPosition = holder.adapterPosition

            notifyItemChanged(previousSelectedPosition)

            notifyItemChanged(selectedPosition)

            listener.reloadSubCategoriesRecyclerView(holder.adapterPosition)
        }
    }

    fun setNewData(categories: List<Category>, currentPosition: Int) {
        mainCategoryList = categories
        selectedPosition = currentPosition
        notifyDataSetChanged()
    }
}

interface MainCategoriesRecyclerViewListener {
    fun reloadSubCategoriesRecyclerView(index : Int)
}