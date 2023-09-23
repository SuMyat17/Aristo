package com.aristo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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
    }

    private fun setUpAdapters() {
        mCartAdapter = CartAdapter()
        binding.rvCart.adapter = mCartAdapter
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

}