package com.aristo.view.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R
import com.aristo.utils.processColorCode
import com.aristo.model.Category
import com.aristo.databinding.ViewHolderNewItemsBinding
import com.aristo.model.NewProduct
import com.aristo.network.FirebaseApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions

class NewItemsAdapter(private var listener: NewItemListener) : RecyclerView.Adapter<NewItemsAdapter.NewItemViewHolder>() {

    private var dataList: List<Category> = listOf()
    private var newProductList: List<NewProduct> = listOf()

    class NewItemViewHolder(private val binding: ViewHolderNewItemsBinding, val context: Context, var listener: NewItemListener) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category, newProductImage: String?) {
            itemView.setOnClickListener {
                listener.onTapNewItem(category)
            }

            binding.tvItemName.text = category.title

            Log.d("bind Datas", "bind Datas: ${category.imageURL}")

            if (category.colorCode != "" && category.colorCode.count() in 7..10 && newProductImage == null){
                binding.progressBar.visibility = View.GONE
                binding.ivFullImage.foreground = ColorDrawable(Color.parseColor(processColorCode(category.colorCode)))
                binding.ivSmallImage.foreground = ColorDrawable(Color.parseColor(processColorCode(category.colorCode)))

            } else {
                binding.progressBar.visibility = View.VISIBLE
                binding.ivFullImage.foreground = null
                binding.ivSmallImage.foreground = null

                val image = if (newProductImage?.isNotEmpty() == true) newProductImage else category.imageURL

                Glide.with(context).load(image).into(binding.ivFullImage)
                Glide.with(context).load(image).apply(RequestOptions.placeholderOf(R.drawable.ic_placeholder))
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            binding.progressBar.visibility = View.GONE
                            binding.ivSmallImage.setImageResource(R.drawable.ic_placeholder)
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            binding.progressBar.visibility = View.GONE
                            return false
                        }
                    })
                    .into(binding.ivSmallImage)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewItemViewHolder {
        val binding = ViewHolderNewItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewItemViewHolder(binding, parent.context, listener)
    }

    override fun onBindViewHolder(holder: NewItemViewHolder, position: Int) {
        if (dataList.isNotEmpty() && newProductList.isNotEmpty()) {

            var newProductImage: String? = null

            newProductList.forEach {
                if (dataList[position].id == it.id) {
                    newProductImage = it.productImage
                }
            }
            holder.bind(dataList[position], newProductImage)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setNewData(categoryList: List<Category>, sortedList: List<NewProduct>) {
        dataList = categoryList
        newProductList = sortedList
        notifyDataSetChanged()
    }

    interface NewItemListener {
        fun onTapNewItem(category: Category)
    }
}