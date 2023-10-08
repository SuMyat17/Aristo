package com.aristo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aristo.R
import com.aristo.databinding.ActivityMainCategoriesBinding
import com.aristo.viewModel.CategoriesViewModel

class MainCategoriesActivity : AppCompatActivity(), MainCategoriesRecyclerViewListener {

    private lateinit var binding : ActivityMainCategoriesBinding

    private lateinit var categoriesViewModel: CategoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        categoriesViewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]
        //categoriesViewModel.loadCategories()

        setRecyclerViewAdapter()

        binding.ibBack.setOnClickListener {
            finish()
        }

    }

    fun setRecyclerViewAdapter(){
        // Main Categories Recycler View
        val mainCatLayoutManager = LinearLayoutManager(this)
        binding.rvMainCategories.layoutManager = mainCatLayoutManager
        //binding.rvMainCategories.adapter = MainCategoriesListRecyclerViewAdapter(this,categoriesViewModel.categoryList)
        binding.rvMainCategories.adapter = MainCategoriesListRecyclerViewAdapter(this)

        // Sub Categories Recycler View
        val subCatLayoutManager = GridLayoutManager(this,2)
        binding.rvSubCategories.layoutManager = subCatLayoutManager
        //binding.rvSubCategories.adapter = SubCategoriesListRecyclerViewAdapter(this,categoriesViewModel.categoryList[0].childCategories)
        binding.rvSubCategories.adapter = SubCategoriesListRecyclerViewAdapter(this)
    }

    // Reload Sub Categories Recycler View when select main categories recycler view
    override fun reloadSubCategoriesRecyclerView(index : Int) {
        binding.rvSubCategories.adapter = SubCategoriesListRecyclerViewAdapter(this)
        //binding.rvSubCategories.adapter = SubCategoriesListRecyclerViewAdapter(this,categoriesViewModel.categoryList[index].childCategories)
    }
}