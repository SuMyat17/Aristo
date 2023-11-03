package com.aristo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aristo.R
import com.aristo.data.CartListDataHolder
import com.aristo.model.Category
import com.aristo.view.adapters.ChildCategoryListAdapter
import com.aristo.view.adapters.MainCategoryListAdapter
import com.aristo.databinding.ActivityMainCategoriesBinding
import com.aristo.model.NewCategory
import com.aristo.network.FirebaseApi
import com.aristo.viewModel.CategoriesViewModel
import com.google.android.material.bottomnavigation.BottomNavigationItemView

class MainCategoriesActivity : AppCompatActivity(), MainCategoryListAdapter.MainCategoriesRecyclerViewListener {

    private lateinit var binding : ActivityMainCategoriesBinding

    private var firebaseApi = FirebaseApi()

    private lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var mMainCategoryAdapter: MainCategoryListAdapter
    private lateinit var mSubCategoryAdapter: ChildCategoryListAdapter

    private var categoryList: List<Category> = listOf()
    private var newItemsList: List<NewCategory> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getIntExtra("main category position", 0)

        binding.ibBack.setOnClickListener {
            finish()
        }

        setRecyclerViewAdapter()

        firebaseApi.getNewProducts { isSuccess, data ->
            data?.let{newItemsList = it}
        }

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

    override fun onResume() {
        super.onResume()
        showBadge()
    }

    private fun showBadge() {
        if (CartListDataHolder.instance.cartList.size != 0) {
            binding.tvNotificationBadge.visibility = View.VISIBLE
            binding.tvNotificationBadge.text = CartListDataHolder.instance.cartList.size.toString()
        } else {
            binding.tvNotificationBadge.visibility = View.GONE
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