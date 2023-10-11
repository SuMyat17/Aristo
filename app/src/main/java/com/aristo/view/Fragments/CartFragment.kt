package com.aristo.view.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aristo.Manager.*
import com.aristo.Manager.Network.Firebase
import com.aristo.R
import com.aristo.databinding.FragmentCartBinding
import com.aristo.model.Cart
import com.aristo.view.adapters.CartAdapter

class CartFragment : Fragment(), CartAdapter.CartItemListener {

    private lateinit var binding: FragmentCartBinding
    private lateinit var mCartAdapter: CartAdapter
    private var cartList = arrayListOf<Cart>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapters()

        SharedPreferenceManager.initializeSharedPref(requireContext(), "cartList")
        if (SharedPreferenceManager.getCartList().isNotEmpty()) {
            cartList = ArrayList(SharedPreferenceManager.getCartList())
        }

        binding.tvTotalQuantity.text = SharedPreferenceManager.getCartList().size.toString()
        mCartAdapter.setNewData(cartList)

        binding.btnOrder.setOnClickListener {
            showOrderConfirmAlert()
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
        val dialog = builder.create()

        btnCancel.setOnClickListener {
            dialog.cancel()
        }

        btnConfirm.setOnClickListener {

            Firebase.getShopDetail(requireActivity()) { isSuccess, data ->

                data?.let{
                    val phoneNo = data.viber
                    val modifiedPhoneNo = replacePrefix(phoneNo)
                    sendMessageToViber(requireActivity(),modifiedPhoneNo)

                    dialog.cancel()
                }
            }

        }

        dialog.show()
    }

    override fun onTapDelete(cart: Cart) {
        val itemToUpdate = cartList.find { it.product?.id == cart.product?.id }
        if (itemToUpdate != null) {
            cartList.remove(itemToUpdate)
        }
        SharedPreferenceManager.saveCartList(cartList.toList())
        mCartAdapter.setNewData(cartList)

        binding.tvTotalQuantity.text = SharedPreferenceManager.getCartList().size.toString()
    }

    override fun onTapAdd() {
        binding.tvTotalQuantity.text = SharedPreferenceManager.getCartList().size.toString()
    }

    override fun onTapMinus() {
        binding.tvTotalQuantity.text = SharedPreferenceManager.getCartList().size.toString()
    }

}