package com.aristo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R
import com.aristo.databinding.ActivityChildCategoriesBinding
import com.aristo.databinding.ActivityMainCategoriesBinding
import com.aristo.model.ChildCategories

class ChildCategoriesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChildCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChildCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerViewAdapter()

        binding.ibBack.setOnClickListener {
            finish()
        }

    }

    fun setRecyclerViewAdapter(){
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