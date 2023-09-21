package com.aristo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R
import com.aristo.model.ChildCategories
import com.aristo.model.Product

class ChildCategoriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_categories)

        // Inside your DestinationActivity's onCreate() or wherever you need to access the ArrayList
        val childCategoriesList: ArrayList<ChildCategories>? = intent.getSerializableExtra("childCategoriesList") as? ArrayList<ChildCategories>

        // Sub Categories Recycler View
        val subCatRV = findViewById<RecyclerView>(R.id.rv_sub_categories)
        val subCatLayoutManager = GridLayoutManager(this,2)
        subCatRV.layoutManager = subCatLayoutManager
        subCatRV.adapter =
            childCategoriesList?.let { ChildCategoriesListRecyclerViewAdapter(this, it) }

    }
}