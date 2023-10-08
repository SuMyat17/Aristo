package com.aristo.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aristo.R
import com.aristo.databinding.FragmentCartBinding
import com.aristo.view.adapters.CartAdapter

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var mCartAdapter: CartAdapter

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

        binding.btnOrder.setOnClickListener {
            showOrderConfirmAlert()
        }
    }

    private fun setUpAdapters() {
        mCartAdapter = CartAdapter()
        binding.rvCart.adapter = mCartAdapter
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    fun showOrderConfirmAlert() {
        val builder = AlertDialog.Builder(requireContext())
        val customView = LayoutInflater.from(requireContext()).inflate(R.layout.order_confirm_alert,null)
        builder.setView(customView)
        builder.setCancelable(false)

        val btnCancel : Button = customView.findViewById(R.id.btnCancel)
        val btnConfirm : Button = customView.findViewById(R.id.btnConfirm)
        val dialog = builder.create()

        btnCancel.setOnClickListener {
            dialog.cancel()
        }

        btnConfirm.setOnClickListener {
            Toast.makeText(requireContext(),"Click Confirm", Toast.LENGTH_LONG).show()

            dialog.cancel()
        }

        dialog.show()
    }

}