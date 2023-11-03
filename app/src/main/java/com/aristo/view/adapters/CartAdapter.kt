package com.aristo.view.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aristo.data.CartListDataHolder
import com.aristo.databinding.ViewHolderCartBinding
import com.aristo.model.Cart
import com.aristo.utils.processColorCode
import com.bumptech.glide.Glide

class CartAdapter(private var listener: CartItemListener) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private var cartList = listOf<Cart>()

    class CartViewHolder(var binding: ViewHolderCartBinding, val context: Context, var listener: CartItemListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: Cart, position: Int) {

            if (cart.product?.colorCode != "" && cart.product?.colorCode?.count() in 7..10){
                binding.ivProductImage.foreground = ColorDrawable(Color.parseColor(processColorCode(cart.product!!.colorCode)))
            } else if (cart.product?.imageURL != "") {
                binding.ivProductImage.foreground = null
                Glide.with(context).load(cart.product?.imageURL).into(binding.ivProductImage)
            }

            binding.tvProductName.text = cart.product?.title

            val cartList = CartListDataHolder.instance.cartList

            var quantity = cart.quantity

            binding.tvQuantity.text = getQuantityText(quantity, cart.product?.type)

            binding.btnAdd.setOnClickListener {
                quantity+=1
                binding.btnQuantity.text = quantity.toString()
                binding.tvQuantity.text = getQuantityText(quantity, cart.product?.type)

                cartList.forEach {
                    if (it.product?.id == cart.product?.id) {
                        it.quantity = quantity
                    }
                }
                CartListDataHolder.instance.cartList = cartList
                listener.onTapAdd()
            }

            binding.btnMinus.setOnClickListener {
                if (quantity >1) { quantity-=1 }
                else {
                    listener.onTapDelete(cart)
                    return@setOnClickListener
                }
                binding.btnQuantity.text = quantity.toString()
                binding.tvQuantity.text = getQuantityText(quantity, cart.product?.type)
                cartList.forEach {
                    if (it.product?.id == cart.product?.id) {
                        it.quantity = quantity
                    }
                }
                CartListDataHolder.instance.cartList = cartList
                listener.onTapMinus()
            }
            binding.btnQuantity.text = quantity.toString()

//            binding.btnDelete.setOnClickListener {
//                listener.onTapDelete(cart)
//            }
        }

        private fun getQuantityText(quantity: Int, type: String?): String {
            var text = "အရေအတွက် - ($quantity) "
            text += type
//            type?.let {
//                when {
//                    it.contains("ထည်") -> text += " ထည် \n"
//                    it.contains("လိပ်") -> text += " လိပ် \n"
//                    it.contains("စီး") -> text += " စီး \n"
//                    it.contains("ကွင်း") -> text += " ကွင်း \n"
//                }
//            }
            return text
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