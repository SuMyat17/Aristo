package com.aristo.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aristo.data.CartListDataHolder
import com.aristo.utils.processColorCode
import com.aristo.model.Category
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

        if (product?.colorCode != "" && product?.colorCode?.count() in 7..10){
            binding.ivProduct.foreground = ColorDrawable(Color.parseColor(processColorCode(product!!.colorCode)))
            binding.ivZoom.visibility = View.GONE
        } else if (product?.imageURL != "") {
            binding.ivProduct.foreground = null
            binding.ivZoom.visibility = View.VISIBLE
            Glide.with(this).load(product?.imageURL).into(binding.ivProduct)
        }
        binding.tvType.text = product?.type
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
            var cartList = arrayListOf<Cart>()
            if (CartListDataHolder.instance.cartList?.isNotEmpty() == true) {
                cartList = CartListDataHolder.instance.cartList as ArrayList<Cart>
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
            CartListDataHolder.instance.cartList = cartList
            Toast.makeText(this, "Successfully added to cart", Toast.LENGTH_LONG).show()
            finish()
        }

        binding.ivProduct.setOnClickListener {

            if (product?.imageURL != ""){
                val intent = Intent(this, FullImageActivity::class.java)
                intent.putExtra("image",product?.imageURL)
                startActivity(intent)
            }

        }
    }
}