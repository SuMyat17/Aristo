package com.aristo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aristo.databinding.FragmentInformationBinding

class InformationFragment : Fragment() {

    private lateinit var binding: FragmentInformationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

}