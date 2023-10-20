package com.aristo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aristo.model.Category
import com.aristo.view.adapters.ChildCategoryListAdapter
import com.aristo.view.adapters.MainCategoryListAdapter
import com.aristo.databinding.ActivityMainCategoriesBinding
import com.aristo.network.FirebaseApi
import com.aristo.viewModel.CategoriesViewModel

class MainCategoriesActivity : AppCompatActivity(), MainCategoryListAdapter.MainCategoriesRecyclerViewListener {

    private lateinit var binding : ActivityMainCategoriesBinding

    private var firebaseApi = FirebaseApi()

    private lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var mMainCategoryAdapter: MainCategoryListAdapter
    private lateinit var mSubCategoryAdapter: ChildCategoryListAdapter

    private var categoryList: List<Category> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getIntExtra("main category position", 0)

        binding.ibBack.setOnClickListener {
            finish()
        }

        setRecyclerViewAdapter()

        firebaseApi.getMainCategoryData {isSuccess, data ->

            if (isSuccess) {
                if (data != null) {
                    categoryList = data
                    mMainCategoryAdapter.setNewData(categoryList, position)
                    mSubCategoryAdapter.setNewData(categoryList[position].subCategories.values.toList())
                }
            } else {
                Toast.makeText(this, "Can't retrieve data.", Toast.LENGTH_LONG).show()
            }
            binding.mainLoading.visibility = View.GONE
        }

        binding.ibCart.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("fragmentToOpen", "Cart")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    private fun setRecyclerViewAdapter(){
        // Main Categories Recycler View
        mMainCategoryAdapter = MainCategoryListAdapter(this, this)
        binding.rvMainCategories.layoutManager = LinearLayoutManager(this)
        binding.rvMainCategories.adapter = mMainCategoryAdapter

        // Sub Categories Recycler View
        mSubCategoryAdapter = ChildCategoryListAdapter(this)
        binding.rvSubCategories.layoutManager = GridLayoutManager(this,2)
        binding.rvSubCategories.adapter = mSubCategoryAdapter
    }

    // Reload Sub Categories Recycler View when select main categories recycler view
    override fun reloadSubCategoriesRecyclerView(index : Int) {
        mSubCategoryAdapter.setNewData(categoryList[index].subCategories.values.toList())
    }
}