package com.aristo.view.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aristo.Manager.*
import com.aristo.Manager.Network.Firebase
import com.aristo.R
import com.aristo.model.Category
import com.aristo.databinding.FragmentCartBinding
import com.aristo.databinding.OrderConfirmAlertBinding
import com.aristo.model.Cart
import com.aristo.network.FirebaseApi
import com.aristo.view.adapters.CartAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class CartFragment : Fragment(), CartAdapter.CartItemListener {

    private lateinit var binding: FragmentCartBinding
    private lateinit var mCartAdapter: CartAdapter
    private var cartList = arrayListOf<Cart>()
    private var filteredCartList = arrayListOf<Cart>()

    private var firebaseApi = FirebaseApi()
    private var isFound = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE

        setUpAdapters()

        SharedPreferenceManager.initializeSharedPref(requireContext(), "cartList")
        if (SharedPreferenceManager.getCartList().isNotEmpty()) {
            cartList = ArrayList(SharedPreferenceManager.getCartList())
        }

        firebaseApi.getMainCategoryData{ isSuccess, data ->
            if (isSuccess) {
                cartList.forEach outerLoop@{ cart ->
                    isFound = false
                    data?.forEach { mainCategory ->
                        if (isFound) {
                            return@outerLoop
                        } else {
                            findCategoryWithEmptySubcategories(mainCategory, cart)
                        }
                    }
                }
                SharedPreferenceManager.saveCartList(filteredCartList.toList())
                mCartAdapter.setNewData(filteredCartList)
                binding.tvTotalQuantity.text = SharedPreferenceManager.getCartList().size.toString()
            } else {
                Toast.makeText(requireContext(), "Can't retrieve data.", Toast.LENGTH_LONG).show()
            }
            binding.progressBar.visibility = View.GONE
        }

        binding.btnOrder.setOnClickListener {

            if (cartList.isNotEmpty()) {
                showOrderConfirmAlert()
            } else {
                Toast.makeText(requireContext(), "Please add some products", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun findCategoryWithEmptySubcategories(rootCategory: Category, cart: Cart){
        if (isFound) {
            return
        }
        if (rootCategory.subCategories.isEmpty()) {
            isFound = rootCategory.id == cart.product!!.id
            if (isFound) {
                filteredCartList.add(cart)
            }
        }
        for (subCategory in rootCategory.subCategories.values) {
            findCategoryWithEmptySubcategories(subCategory, cart)
        }
    }

    private fun setUpAdapters() {
        mCartAdapter = CartAdapter(this)
        binding.rvCart.adapter = mCartAdapter
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    fun showOrderConfirmAlert() {
        val builder = AlertDialog.Builder(requireContext())
        val customView = LayoutInflater.from(requireContext())
            .inflate(R.layout.order_confirm_alert,null)
        builder.setView(customView)
        builder.setCancelable(false)

        val btnCancel : Button = customView.findViewById(R.id.btnCancel)
        val btnConfirm : Button = customView.findViewById(R.id.btnConfirm)
        val gridLayout: GridLayout = customView.findViewById(R.id.gridLayout)

        val dialog = builder.create()

        var message = ""
        cartList.forEach {
            message += "${it.product?.title} ${it.quantity} ${it.product?.type} \n"
//            it.product?.type?.let { type ->
//                when {
//                    type.contains("ထည်") -> message += " ထည် \n"
//                    type.contains("လိပ်") -> message += " လိပ် \n"
//                    type.contains("စီး") -> message += " စီး \n"
//                    type.contains("ကွင်း") -> message += " ကွင်း \n"
//                }
//            }

            val itemNameTextView = TextView(context)
            itemNameTextView.text = "${it.product?.title} \n"
            itemNameTextView.setTextColor(resources.getColor(R.color.black))
            itemNameTextView.textSize = 16f
            val params1 = GridLayout.LayoutParams()
            params1.width = 0
            params1.height = GridLayout.LayoutParams.WRAP_CONTENT
            params1.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1.5f)
            itemNameTextView.layoutParams = params1
            val itemQuantityTextView = TextView(context)
            var text = "${it.quantity} ${it.product?.type}"
//            it.product?.type?.let { type ->
//                when {
//                    type.contains("ထည်") -> text += " ထည် \n"
//                    type.contains("လိပ်") -> text += " လိပ် \n"
//                    type.contains("စီး") -> text += " စီး \n"
//                    type.contains("ကွင်း") -> text += " ကွင်း \n"
//                }
//            }
            itemQuantityTextView.text = text
            itemQuantityTextView.setTextColor(resources.getColor(R.color.black))
            itemQuantityTextView.textSize = 16f
            val params2 = GridLayout.LayoutParams()
            params2.width = 0
            params2.height = GridLayout.LayoutParams.WRAP_CONTENT
            params2.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 0.5f)
            itemNameTextView.layoutParams = params2

            gridLayout.addView(itemNameTextView)
            gridLayout.addView(itemQuantityTextView)

        }

        btnCancel.setOnClickListener {
            dialog.cancel()
        }

        btnConfirm.setOnClickListener {
            sendMessageToViber(requireActivity(), message)
            SharedPreferenceManager.clearCartList()
            cartList.clear()
            binding.tvTotalQuantity.text = "0"
            mCartAdapter.setNewData(cartList)
            dialog.cancel()
        }
        dialog.show()
    }

    private fun deleteCartDialog(cart: Cart){
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = OrderConfirmAlertBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        builder.setCancelable(false)

        val dialog = builder.create()

        dialogBinding.tvAlertTitle.text = "Order List ထဲမှဖျက်ရန်"
        dialogBinding.btnConfirm.text = "Confirm"

        val itemNameTextView = TextView(context)
        itemNameTextView.text = "Order List ထဲမှပစ္စည်းကို ဖျက်ရန်သေချာပါသလား"
        itemNameTextView.setTextColor(resources.getColor(R.color.black))
        itemNameTextView.textSize = 16f
        itemNameTextView.gravity = Gravity.CENTER
        dialogBinding.gridLayout.addView(itemNameTextView)

        dialogBinding.btnConfirm.setOnClickListener {
            val itemToUpdate = cartList.find { it.product?.id == cart.product?.id }
            if (itemToUpdate != null) {
                cartList.remove(itemToUpdate)
            }
            SharedPreferenceManager.saveCartList(cartList.toList())
            mCartAdapter.setNewData(cartList)

            binding.tvTotalQuantity.text = SharedPreferenceManager.getCartList().size.toString()
            dialog.cancel()
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
    }


    override fun onTapDelete(cart: Cart) {
        deleteCartDialog(cart)
//        val itemToUpdate = cartList.find { it.product?.id == cart.product?.id }
//        if (itemToUpdate != null) {
//            cartList.remove(itemToUpdate)
//        }
//        SharedPreferenceManager.saveCartList(cartList.toList())
//        mCartAdapter.setNewData(cartList)
//
//        binding.tvTotalQuantity.text = SharedPreferenceManager.getCartList().size.toString()
    }

    override fun onTapAdd() {
        binding.tvTotalQuantity.text = SharedPreferenceManager.getCartList().size.toString()
    }

    override fun onTapMinus() {
        binding.tvTotalQuantity.text = SharedPreferenceManager.getCartList().size.toString()
    }

}