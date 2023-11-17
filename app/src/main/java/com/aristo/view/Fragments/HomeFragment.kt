package com.aristo.view.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.aristo.model.Category
import com.aristo.databinding.FragmentHomeBinding
import com.aristo.model.NewCategory
import com.aristo.network.FirebaseApi
import com.aristo.view.ChildCategoriesActivity
import com.aristo.view.MainCategoriesActivity
import com.aristo.view.ProductDetailActivity
import com.aristo.view.adapters.HomeCategoryListAdapter
import com.aristo.view.adapters.NewItemsAdapter
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

const val TOPIC = "/topics/myTopic2"
class HomeFragment : Fragment(), HomeCategoryListAdapter.HomeMainCategoryListener, NewItemsAdapter.NewItemListener {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var mNewItemsAdapter: NewItemsAdapter
    private lateinit var mCategoryAdapter: HomeCategoryListAdapter

    private var categoryList: List<Category> = listOf()
    private var sortedList: List<NewCategory> = arrayListOf()
    private var newCategoryList: ArrayList<Category> = arrayListOf()

    private var firebaseApi = FirebaseApi()
    private var selectedCategory : Category? = null
    private var isFound = false
    private var isFoundAll = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.progressBarNewItem.visibility = View.VISIBLE
        binding.progressBarMain.visibility = View.VISIBLE
        setUpAdapters()

        binding.btnSeeMore.setOnClickListener {
            val intent = Intent(activity, MainCategoriesActivity::class.java)
            startActivity(intent)
        }

//        if (firebaseApi.auth.currentUser == null){
//            firebaseApi.signInUser {success ->
//                if (success){
//                    fetchDatas()
//                    setDeviceToken()
//                }
//            }
//        }
//        else{
            fetchDatas()
            setDeviceToken()
//        }

    }

    private fun setDeviceToken(){
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    // Handle the token here (e.g., send it to your server)
                    addUserDeviceToken(token)
                    Log.d("FCM Token", token)
                } else {
                    Log.e("FCM Token", "Failed to get FCM token")
                }
            }

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
    }

    fun addUserDeviceToken(token : String){
        val databaseReference = FirebaseDatabase.getInstance().getReference("Tokens")

        // Check if the key is not null before adding the data
        databaseReference.child(token).child("token").setValue(token)
            .addOnSuccessListener {
                // Data successfully added to the database
                Log.d("Firebase", "Token added successfully")
            }
            .addOnFailureListener { e ->
                // Failed to add token to the database
                Log.w("Firebase", "Error adding token to database", e)
            }


    }

    private fun fetchDatas(){
        firebaseApi.getMainCategoryData { isSuccess, data ->
            if (isSuccess) {
                newCategoryList.clear()
                if (data?.isNotEmpty() == true) {
                    categoryList = data
                    mCategoryAdapter.setNewData(data)
                } else {
                    binding.rvCategoryList.visibility = View.GONE
                    binding.titleMainCategory.visibility = View.GONE
                }
            } else {
                binding.rvCategoryList.visibility = View.GONE
                binding.titleMainCategory.visibility = View.GONE
                Toast.makeText(requireContext(), "Can't retrieve data.", Toast.LENGTH_LONG).show()
            }
            binding.progressBarMain.visibility = View.GONE
        }

        firebaseApi.getNewProducts { isSucccess, data ->

            if (isSucccess) {
                sortedList = arrayListOf()
                newCategoryList.clear()
                if (data?.isNotEmpty() == true) {
                    sortedList = data.sortedByDescending { it.timeStamp }

                    // new item list
                    sortedList.forEach {  newCategory ->
                        isFoundAll = false
                        categoryList.forEach {  allCategory ->
                            if (!isFoundAll) {
                                findAllNewCategories(allCategory, newCategory)
                            }
                        }
                    }
                    mNewItemsAdapter.setNewData(newCategoryList)


                    binding.flNewItem.visibility = View.VISIBLE
                    binding.tvNewItemTitle.visibility = View.VISIBLE
                    binding.dotsIndicatorNewItems.visibility = View.VISIBLE
                } else {
                    binding.flNewItem.visibility = View.GONE
                    binding.tvNewItemTitle.visibility = View.GONE
                    binding.dotsIndicatorNewItems.visibility = View.GONE
                }
            } else {
                binding.flNewItem.visibility = View.GONE
                binding.tvNewItemTitle.visibility = View.GONE
                binding.dotsIndicatorNewItems.visibility = View.GONE
                Toast.makeText(requireContext(), "Can't retrieve data.", Toast.LENGTH_LONG).show()
            }
            binding.progressBarNewItem.visibility = View.GONE
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
//        val category = Category(id = newProduct.id, title = newProduct.title, price = newProduct.price, imageURL = newProduct.imageURL, new = newProduct.new, colorCode = newProduct.colorCode, type = newProduct.type)

        isFound = false

        if (category.subCategories.isEmpty()) {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("product", category)
            startActivity(intent)
        } else {
            categoryList.forEach { mainCategory ->
                if(!isFound) {
                    findSelectedCategory(mainCategory, category)
                }
            }
            selectedCategory?.let {
                val intent = Intent(context, ChildCategoriesActivity:: class.java)
                intent.putExtra("childCategoriesList", selectedCategory)
                startActivity(intent)
            }
        }
    }

    private fun findSelectedCategory(rootCategory: Category, currentCategory: Category?) {
        if (rootCategory.id == currentCategory?.id) {
            isFound = true
            selectedCategory = rootCategory
        }

        for (subCategory in rootCategory.subCategories.values) {
            findSelectedCategory(subCategory, currentCategory)
        }
    }


    private fun findAllNewCategories(rootCategory: Category, newCategory: NewCategory?) {
        if (rootCategory.id == newCategory?.id) {
            isFoundAll = true
            newCategoryList.add(rootCategory)
        }

        for (subCategory in rootCategory.subCategories.values) {
            findAllNewCategories(subCategory, newCategory)
        }
    }

}