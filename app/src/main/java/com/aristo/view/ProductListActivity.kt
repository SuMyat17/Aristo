package com.aristo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R
import com.aristo.admin.model.Category
import com.aristo.databinding.ActivityMainCategoriesBinding
import com.aristo.databinding.ActivityProductListBinding

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerViewAdapter()

        binding.ibBack.setOnClickListener {
            finish()
        }
    }

    fun setRecyclerViewAdapter(){
        val productList: ArrayList<Category>? = intent.getSerializableExtra("productList") as? ArrayList<Category>

        // Product List Recycler View
        val productListLayoutManager = GridLayoutManager(this,2)
        binding.rvProductList.layoutManager = productListLayoutManager
        binding.rvProductList.adapter =
            productList?.let { ProductListRecyclerViewAdapter(this, it) }

    }
}