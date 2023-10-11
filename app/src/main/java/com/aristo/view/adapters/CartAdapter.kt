package com.aristo.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aristo.Manager.SharedPreferenceManager
import com.aristo.Manager.SharedPreferenceManager.initializeSharedPref
import com.aristo.databinding.ViewHolderCartBinding
import com.aristo.model.Cart
import com.bumptech.glide.Glide

class CartAdapter(private var listener: CartItemListener) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private var cartList = listOf<Cart>()

    class CartViewHolder(var binding: ViewHolderCartBinding, val context: Context, var listener: CartItemListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: Cart, position: Int) {
            Glide.with(context).load(cart.product?.imageURL).into(binding.ivProductImage)
            binding.tvProductName.text = cart.product?.title
            binding.tvQuantity.text = "အရေအတွက် - (${cart.quantity})"

            var quantity = cart.quantity

            initializeSharedPref(context, "cartList")
            val cartList = SharedPreferenceManager.getCartList()

            binding.btnAdd.setOnClickListener {
                quantity+=1
                binding.btnQuantity.text = quantity.toString()
                binding.tvQuantity.text = "အရေအတွက် - ($quantity)"

                cartList.forEach {
                    if (it.product?.id == cart.product?.id) {
                        it.quantity = quantity
                    }
                }
                SharedPreferenceManager.saveCartList(cartList.toList())
                listener.onTapAdd()
            }

            binding.btnMinus.setOnClickListener {
                if (quantity >1) { quantity-=1 }
                else { listener.onTapDelete(cart) }
                binding.btnQuantity.text = quantity.toString()
                binding.tvQuantity.text = "အရေအတွက် - ($quantity)"
                cartList.forEach {
                    if (it.product?.id == cart.product?.id) {
                        it.quantity = quantity
                    }
                }
                SharedPreferenceManager.saveCartList(cartList.toList())
                listener.onTapMinus()
            }
            binding.btnQuantity.text = quantity.toString()

            binding.btnDelete.setOnClickListener {
                listener.onTapDelete(cart)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ViewHolderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, parent.context, listener)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        if (cartList.isNotEmpty()) {
            holder.bind(cartList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    fun setNewData(carts: List<Cart>) {
        cartList = carts
        notifyDataSetChanged()
    }

    interface CartItemListener {
        fun onTapDelete(cart: Cart)
        fun onTapAdd()
        fun onTapMinus()
    }
}