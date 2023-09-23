package com.aristo.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aristo.model.Category
import com.aristo.model.ChildCategories
import com.aristo.model.Product
import com.aristo.model.SubCategories

class CategoriesViewModel : ViewModel() {

    val categoryList = ArrayList<Category>()

    fun loadCategories(){
        categoryList.add(Category(1, "Category 1",
            arrayListOf(
                SubCategories( 1, "Subcategory 1-1",
                    arrayListOf(
                        ChildCategories(1, "Child Category 1-1-1", arrayListOf(),
                            arrayListOf(
                                ChildCategories(1, "Child Category 1-1-1-1",
                                    arrayListOf(
                                        Product(1,"Product 1"),
                                        Product(2,"Product 2")
                                    ),
                                    arrayListOf()
                                ),
                                ChildCategories(2, "Child Category 1-1-2-2",
                                    arrayListOf(
                                        Product(1,"Product 3"),
                                        Product(2,"Product 4")
                                    ),arrayListOf())
                            )
                        ),
                    ChildCategories(2, "Child Category 1-1-2",
                        arrayListOf(
                            Product(1,"Product 3"),
                            Product(2,"Product 4")
                        ),arrayListOf())
                    )),

            SubCategories(id = 2, title = "Subcategory 1-2", arrayListOf(
                ChildCategories(1, "Child Category 1-2-1",
                    arrayListOf(
                        Product(1,"Product 5"),
                        Product(2,"Product 6")
                    ), arrayListOf()),
                ChildCategories(2, "Child Category 1-2-2",
                    arrayListOf(
                        Product(1,"Product 7"),
                        Product(2,"Product 8")
                    ),arrayListOf())
            ))
        )))

        categoryList.add(Category(2, "Category 2", arrayListOf(
            SubCategories(id = 1, title = "Subcategory 2-1", arrayListOf(
                ChildCategories(1, "Child Category 2-1-1",
                    arrayListOf(
                        Product(1,"Product 9"),
                        Product(2,"Product 10")
                    ), arrayListOf()),
                ChildCategories(2, "Child Category 2-1-2",
                    arrayListOf(
                        Product(1,"Product 11"),
                        Product(2,"Product 12")
                    ), arrayListOf())
            )),
            SubCategories(id = 2, title = "Subcategory 2-2", arrayListOf(
                ChildCategories(1, "Child Category 2-2-1",
                    arrayListOf(
                        Product(1,"Product 13"),
                        Product(2,"Product 14")
                    ), arrayListOf()),
                ChildCategories(2, "Child Category 2-2-2",
                    arrayListOf(
                        Product(1,"Product 15"),
                        Product(2,"Product 16")
                    ), arrayListOf())
            ))
        )))

        categoryList.add(Category(3, "Category 3", arrayListOf(
            SubCategories(id = 1, title = "Subcategory 3-1", arrayListOf(
                ChildCategories(1, "Child Category 3-1-1",
                    arrayListOf(
                        Product(1,"Product 17"),
                        Product(2,"Product 18")
                    ), arrayListOf()),
                ChildCategories(2, "Child Category 3-1-2",
                    arrayListOf(
                        Product(1,"Product 19"),
                        Product(2,"Product 20")
                    ), arrayListOf())
            )),
            SubCategories(id = 2, title = "Subcategory 3-2", arrayListOf(
                ChildCategories(1, "Child Category 3-2-1",
                    arrayListOf(
                        Product(1,"Product 21"),
                        Product(2,"Product 22")
                    ), arrayListOf()),
                ChildCategories(2, "Child Category 3-2-2",
                    arrayListOf(
                        Product(1,"Product 23"),
                        Product(2,"Product 24")
                    ), arrayListOf())
            ))
        )))

        Log.d("Tag", "Categories List ${categoryList}")
    }
}