package com.aristo.view.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aristo.utils.processColorCode
import com.aristo.R
import com.aristo.model.Category
import com.aristo.databinding.ViewHolderMainCategoryBinding
import com.bumptech.glide.*
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions

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
                binding.progressBar.visibility = View.VISIBLE
                Glide.with(context).load(category.imageURL).apply(RequestOptions.placeholderOf(R.drawable.ic_placeholder))
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            binding.progressBar.visibility = View.GONE
                            binding.ivFirstCategory.setImageResource(R.drawable.ic_placeholder)
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            binding.progressBar.visibility = View.GONE
                            return false
                        }
                    })
                    .into(binding.ivFirstCategory)

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
            holder.itemView.setBackgroundResource(R.color.grey)
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

    interface MainCategoriesRecyclerViewListener {
        fun reloadSubCategoriesRecyclerView(index : Int)
    }
}
