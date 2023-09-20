package com.aristo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R
import com.aristo.viewModel.CategoriesViewModel

class MainCategoriesActivity : AppCompatActivity() {

    private lateinit var mainCatRV : RecyclerView
    private lateinit var subCatRV : RecyclerView
    private lateinit var categoriesViewModel: CategoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_categories)

        // Initialize ViewModel
        categoriesViewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]

        // Call the loadCategories function to load data

        // Main Categories Recycler View
        mainCatRV = findViewById(R.id.rv_main_categories)
        val mainCatLayoutManager = LinearLayoutManager(this)
        mainCatRV.layoutManager = mainCatLayoutManager
        mainCatRV.adapter = MainCategoriesListRecyclerViewAdapter(this,categoriesViewModel.loadCategories())

        // Sub Categories Recycler View
        subCatRV = findViewById(R.id.rv_sub_categories)
        val subCatLayoutManager = GridLayoutManager(this,2)
        subCatRV.layoutManager = subCatLayoutManager
        subCatRV.adapter = SubCategoriesListRecyclerViewAdapter(this)

        val cartBtn = findViewById<ImageButton>(R.id.ib_cart)
        cartBtn.setOnClickListener {
            val intent = Intent(this, MainCategoriesActivity:: class.java)
            startActivity(intent)
        }

    }
}