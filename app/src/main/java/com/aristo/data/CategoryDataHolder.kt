package com.aristo.data

import com.aristo.admin.model.Category
class CategoryDataHolder private constructor() {

    var categoryList: ArrayList<Category> = ArrayList()

    companion object {
        private var instance: CategoryDataHolder? = null

        fun getInstance(): CategoryDataHolder {
            if (instance == null) {
                instance = CategoryDataHolder()
            }
            return instance!!
        }
    }
}
