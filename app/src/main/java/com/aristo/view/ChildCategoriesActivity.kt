package com.aristo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.aristo.model.Category
import com.aristo.view.adapters.ChildCategoryListAdapter
import com.aristo.databinding.ActivityChildCategoriesBinding

class ChildCategoriesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChildCategoriesBinding
    private lateinit var mSubCategoryAdapter: ChildCategoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChildCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerViewAdapter()

        binding.ibBack.setOnClickListener {
            finish()
        }

    }

    private fun setRecyclerViewAdapter(){
        // Inside your DestinationActivity's onCreate() or wherever you need to access the ArrayList
        val subCategory = intent.getSerializableExtra("childCategoriesList") as Category?
        binding.tvTitle.text = subCategory?.title

        // Sub Categories Recycler View
        mSubCategoryAdapter = ChildCategoryListAdapter(this)
        binding.rvSubCategories.layoutManager = GridLayoutManager(this,2)
        binding.rvSubCategories.adapter = mSubCategoryAdapter
        if (subCategory != null) {
            mSubCategoryAdapter.setNewData(subCategory.subCategories.values.toList())
        }

        binding.ibCart.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("fragmentToOpen", "Cart")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}