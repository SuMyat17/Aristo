package com.aristo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R
import com.aristo.model.ChildCategories
import com.aristo.model.Product

class ProductListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val productList: ArrayList<Product>? = intent.getSerializableExtra("productList") as? ArrayList<Product>

        // Product List Recycler View
        val productListRV = findViewById<RecyclerView>(R.id.rv_productList)
        val productListLayoutManager = GridLayoutManager(this,2)
        productListRV.layoutManager = productListLayoutManager
        productListRV.adapter =
            productList?.let { ProductListRecyclerViewAdapter(this, it) }
    }
}