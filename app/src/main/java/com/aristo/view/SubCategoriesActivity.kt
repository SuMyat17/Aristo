package com.aristo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R

class SubCategoriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_categories)

        // Sub Categories Recycler View
        val subCatRV = findViewById<RecyclerView>(R.id.rv_sub_categories)
        val subCatLayoutManager = GridLayoutManager(this,2)
        subCatRV.layoutManager = subCatLayoutManager
        subCatRV.adapter = SubCategoriesListRecyclerViewAdapter(this)
    }
}