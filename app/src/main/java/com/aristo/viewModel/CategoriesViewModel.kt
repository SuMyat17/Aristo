package com.aristo.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aristo.model.Category
import com.aristo.model.Product

class CategoriesViewModel : ViewModel() {

    val categoryList = ArrayList<Category>()

    fun loadCategories(){
        categoryList.add(Category(1, "Category 1","ImgURL",arrayListOf(),
            arrayListOf(
                Category( 1, "Subcategory 1-1", "ImgURL",arrayListOf(),
                    arrayListOf(
                        Category(1, "Child Category 1-1-1", "ImgURL",arrayListOf(),
                            arrayListOf(
                                Category(1, "Child Category 1-1-1-1","ImgURL",
                                    arrayListOf(
                                        Product(1,"Product 1","ImgURL",1,true),
                                        Product(2,"Product 2","ImgURL",6,false)
                                    ),
                                    arrayListOf()
                                ),
                                Category(2, "Child Category 1-1-2-2","ImgURL",
                                    arrayListOf(
                                        Product(1,"Product 3","ImgURL",2,false),
                                        Product(2,"Product 4","ImgURL",8,false)
                                    ),arrayListOf())
                            )
                        ),
                        Category(2, "Child Category 1-1-2","ImgURL",
                            arrayListOf(
                                Product(1,"Product 3","ImgURL",4,false),
                                Product(2,"Product 4","ImgURL",6,true)
                            ),arrayListOf())
                    )),

                Category(id = 2, title = "Subcategory 1-2", "ImgURL",arrayListOf() ,arrayListOf(
                    Category(1, "Child Category 1-2-1","ImgURL",
                        arrayListOf(
                            Product(1,"Product 5","ImgURL",3,false),
                            Product(2,"Product 6","ImgURL",2,false)
                        ), arrayListOf()),
                    Category(2, "Child Category 1-2-2","ImgURL",
                        arrayListOf(
                            Product(1,"Product 7","ImgURL",1,false),
                            Product(2,"Product 8","ImgURL",5,true)
                        ),arrayListOf())
                ))
            )))

        categoryList.add(Category(2, "Category 2", "ImgURL",arrayListOf() ,arrayListOf(
            Category(id = 1, title = "Subcategory 2-1", "ImgURL",arrayListOf() ,arrayListOf(
                Category(1, "Child Category 2-1-1","ImgURL",
                    arrayListOf(
                        Product(1,"Product 9","ImgURL",4,false),
                        Product(2,"Product 10","ImgURL",2,true)
                    ), arrayListOf()),
                Category(2, "Child Category 2-1-2","ImgURL",
                    arrayListOf(
                        Product(1,"Product 11","ImgURL",9,false),
                        Product(2,"Product 12","ImgURL",7,true)
                    ), arrayListOf())
            )),
            Category(id = 2, title = "Subcategory 2-2", "ImgURL",arrayListOf() ,arrayListOf(
                Category(1, "Child Category 2-2-1","ImgURL",
                    arrayListOf(
                        Product(1,"Product 13","ImgURL",2,true),
                        Product(2,"Product 14","ImgURL",1,false)
                    ), arrayListOf()),
                Category(2, "Child Category 2-2-2","ImgURL",
                    arrayListOf(
                        Product(1,"Product 15","ImgURL",6,false),
                        Product(2,"Product 16","ImgURL",9,false)
                    ), arrayListOf())
            ))
        )))

        categoryList.add(Category(3, "Category 3", "ImgURL",arrayListOf(),arrayListOf(
            Category(id = 1, title = "Subcategory 3-1", "ImgURL",arrayListOf() ,arrayListOf(
                Category(1, "Child Category 3-1-1","ImgURL",
                    arrayListOf(
                        Product(1,"Product 17","ImgURL",2,false),
                        Product(2,"Product 18","ImgURL",4,false)
                    ), arrayListOf()),
                Category(2, "Child Category 3-1-2","ImgURL",
                    arrayListOf(
                        Product(1,"Product 19","ImgURL",1,true),
                        Product(2,"Product 20","ImgURL",2,false)
                    ), arrayListOf())
            )),
            Category(id = 2, title = "Subcategory 3-2","ImgURL", arrayListOf() ,arrayListOf(
                Category(1, "Child Category 3-2-1","ImgURL",
                    arrayListOf(
                        Product(1,"Product 21","ImgURL",8,true),
                        Product(2,"Product 22","ImgURL",1,false)
                    ), arrayListOf()),
                Category(2, "Child Category 3-2-2","ImgURL",
                    arrayListOf(
                        Product(1,"Product 23","ImgURL",3,false),
                        Product(2,"Product 24","ImgURL",5,false)
                    ), arrayListOf())
            ))
        )))

        Log.d("Tag", "Categories List ${categoryList}")
    }
}