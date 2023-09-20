package com.aristo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aristo.model.Category

class CategoriesViewModel : ViewModel() {

    // LiveData to hold the list of categories\
    private val categoryList = ArrayList<Category>()

    // Function to load categories (you can replace this with your data source)
    fun loadCategories() : ArrayList<Category> {

        categoryList.add(Category(1, "Category 1"))
        categoryList.add(Category(2, "Category 2"))
        categoryList.add(Category(3, "Category 3"))

        return categoryList

    }
}