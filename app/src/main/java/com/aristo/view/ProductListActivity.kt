package com.aristo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R
import com.aristo.databinding.ActivityMainCategoriesBinding
import com.aristo.databinding.ActivityProductListBinding
import com.aristo.model.ChildCategories
import com.aristo.model.Product

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productList: ArrayList<Product>? = intent.getSerializableExtra("productList") as? ArrayList<Product>

        // Product List Recycler View
        val productListLayoutManager = GridLayoutManager(this,2)
        binding.rvProductList.layoutManager = productListLayoutManager
        binding.rvProductList.adapter =
            productList?.let { ProductListRecyclerViewAdapter(this, it) }
    }
}