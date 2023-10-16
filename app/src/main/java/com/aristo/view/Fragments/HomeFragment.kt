package com.aristo.view.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.aristo.R
import com.aristo.admin.model.Category
import com.aristo.databinding.FragmentHomeBinding
import com.aristo.network.FirebaseApi
import com.aristo.view.MainCategoriesActivity
import com.aristo.view.ProductDetailActivity
import com.aristo.view.adapters.HomeCategoryListAdapter
import com.aristo.view.adapters.NewItemsAdapter

class HomeFragment : Fragment(), HomeCategoryListAdapter.HomeMainCategoryListener, NewItemsAdapter.NewItemListener {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var mNewItemsAdapter: NewItemsAdapter
    private lateinit var mCategoryAdapter: HomeCategoryListAdapter

    private var categoryList: List<Category> = listOf()
    private var newItemList: ArrayList<Category> = arrayListOf()
    private var isDotRemoved = false

    private var firebaseApi = FirebaseApi()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.progressBar.visibility = View.VISIBLE
        setUpAdapters()

        binding.btnSeeMore.setOnClickListener {
            val intent = Intent(activity, MainCategoriesActivity::class.java)
            startActivity(intent)
        }
        firebaseApi.getMainCategoryData {isSuccess, data ->
            newItemList.clear()

            if (isSuccess) {
                if (data != null) {
                    categoryList = data
                    mCategoryAdapter.setNewData(data)

                    data.forEach { mainCategory ->
                        findCategoryWithEmptySubcategories(mainCategory)
                    }

                    mNewItemsAdapter.setNewData(newItemList)

                }
            } else {
                Toast.makeText(requireContext(), "Can't retrieve data.", Toast.LENGTH_LONG).show()
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun findCategoryWithEmptySubcategories(rootCategory: Category) {
        if (rootCategory.subCategories.isEmpty()) {
            if (rootCategory.new) {
                newItemList.add(rootCategory)
            }
        }

        for (subCategory in rootCategory.subCategories.values) {
            findCategoryWithEmptySubcategories(subCategory)
        }
    }

    private fun setUpAdapters() {

        mNewItemsAdapter = NewItemsAdapter(this)
        binding.viewPagerNewItems.adapter = mNewItemsAdapter
        binding.dotsIndicatorNewItems.attachTo(binding.viewPagerNewItems)

        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                val currentItem = binding.viewPagerNewItems.currentItem
                val nextItem = if (currentItem == mNewItemsAdapter.itemCount - 1) 0 else currentItem + 1

                binding.viewPagerNewItems.currentItem = nextItem

                handler.removeCallbacks(this)
                handler.postDelayed(this, 2500)
            }
        }

        val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                        handler.removeCallbacks(runnable)
                    }
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        handler.postDelayed(runnable, 2500)
                    }
                }
            }
        }
        binding.viewPagerNewItems.registerOnPageChangeCallback(pageChangeCallback)
        handler.postDelayed(runnable, 2500)

        mCategoryAdapter = HomeCategoryListAdapter(this)
        binding.rvCategoryList.adapter = mCategoryAdapter
        binding.rvCategoryList.layoutManager = GridLayoutManager(requireContext(),2)

    }

    override fun onTapHomeMainCategory(position: Int) {
        val intent = Intent(context, MainCategoriesActivity::class.java)
        intent.putExtra("main category position", position)
        startActivity(intent)
    }

    override fun onTapNewItem(category: Category) {
        val intent = Intent(context, ProductDetailActivity:: class.java)
        intent.putExtra("product", category)
        startActivity(intent)
    }
}