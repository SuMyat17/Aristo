package com.aristo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.aristo.databinding.FragmentHomeBinding
import com.aristo.view.adapters.HomeCategoryListAdapter
import com.aristo.view.adapters.NewItemsAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var mNewItemsAdapter: NewItemsAdapter
    private lateinit var mCategoryAdapter: HomeCategoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setUpAdapters()
    }

    private fun setUpAdapters() {

        mNewItemsAdapter = NewItemsAdapter()
        binding.viewPagerNewItems.adapter = mNewItemsAdapter
        binding.dotsIndicatorNewItems.attachTo(binding.viewPagerNewItems)

        mCategoryAdapter = HomeCategoryListAdapter()
        binding.rvCategoryList.adapter = mCategoryAdapter
        binding.rvCategoryList.layoutManager = GridLayoutManager(requireContext(),2)

    }

}