package com.aristo.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aristo.admin.model.Category
import com.aristo.databinding.ViewHolderCategoryListBinding
import com.aristo.view.ChildCategoriesActivity
import com.aristo.view.ProductDetailActivity
import com.bumptech.glide.Glide

class ChildCategoryListAdapter(private val context: Context) : RecyclerView.Adapter<ChildCategoryListAdapter.SubCategoryListViewHolder>() {

    private var subCategoryList: List<Category> = listOf()

    class SubCategoryListViewHolder(private var binding: ViewHolderCategoryListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(category: Category, context: Context, position: Int) {
            binding.tvFirstCategory.text = category.title
            Glide.with(context).load(category.imageURL).into(binding.ivFirstCategory)

//            if (category.subCategories.isEmpty()) {
//                if (category.new) {
//                    binding.ivNew.visibility = View.VISIBLE
//                }
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryListViewHolder {
        val itemView = ViewHolderCategoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubCategoryListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return subCategoryList.size
    }

    override fun onBindViewHolder(holder: SubCategoryListViewHolder, position: Int) {

        holder.bind(subCategoryList[position],context,position)

        holder.itemView.setOnClickListener {

            if (subCategoryList[position].subCategories.isNotEmpty()){
                val intent = Intent(context, ChildCategoriesActivity:: class.java)
                intent.putExtra("childCategoriesList", subCategoryList[position])
                context.startActivity(intent)
            } else {
                val intent = Intent(context, ProductDetailActivity:: class.java)
                intent.putExtra("product", subCategoryList[position])
                context.startActivity(intent)
            }
        }
    }

    fun setNewData(categories: List<Category>) {
        subCategoryList = categories
        notifyDataSetChanged()
    }

}