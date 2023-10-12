package com.aristo.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aristo.Manager.SharedPreferenceManager
import com.aristo.Manager.SharedPreferenceManager.initializeSharedPref
import com.aristo.Manager.processColorCode
import com.aristo.admin.model.Category
import com.aristo.databinding.ActivityProductDetailBinding
import com.aristo.model.Cart
import com.bumptech.glide.Glide

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductDetailBinding
    private var product : Category? = Category()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpListeners()

        product = intent.getSerializableExtra("product") as? Category

        binding.tvTitle.text = product?.title
        if (product?.price != 0) {
            binding.tvPrice.visibility = View.VISIBLE
            binding.tvPrice.text = "စျေးနှုန်း - ${product?.price.toString()} "
        }

        if (product != null){
            if(product!!.colorCode != ""){
                binding.ivProduct.foreground = ColorDrawable(Color.parseColor(processColorCode(
                    product!!.colorCode)))
            }
            else{
                Glide.with(this).load(product?.imageURL).into(binding.ivProduct)
            }
        }

    }

    private fun setUpListeners() {
        var quantity = binding.btnQuantity.text.toString().toInt()

        binding.ibBack.setOnClickListener {
            super.onBackPressed()
        }

        binding.btnAdd.setOnClickListener {
            quantity+=1
            binding.btnQuantity.text = quantity.toString()
        }

        binding.btnMinus.setOnClickListener {
            if (quantity >0) { quantity-=1 }
            binding.btnQuantity.text = quantity.toString()
        }

        binding.btnAddToCart.setOnClickListener {
            initializeSharedPref(this, "cartList")
            var cartList = arrayListOf<Cart>()
            if (SharedPreferenceManager.getCartList().isNotEmpty()) {
                cartList = ArrayList(SharedPreferenceManager.getCartList())
            }

            product?.let{ product->
                if (cartList.isNotEmpty()) {

                    val itemToUpdate = cartList.find { it.product?.id == product.id }
                    if (itemToUpdate != null) {
                        itemToUpdate.quantity += quantity
                    } else {
                        cartList.add(Cart(product, quantity))
                    }
                } else {
                    cartList.add(Cart(product, quantity))
                }
            }
            SharedPreferenceManager.saveCartList(cartList.toList())
            Toast.makeText(this, "Successfully added to cart", Toast.LENGTH_LONG).show()
        }
    }
}