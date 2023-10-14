package com.aristo.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aristo.Manager.openEmailApp
import com.aristo.Manager.openFacebookApp
import com.aristo.Manager.openViberApp
import com.aristo.Manager.replacePrefix
import com.aristo.Manager.setUnderLineForLinks
import com.aristo.databinding.FragmentDeveloperInformationBinding

class DeveloperInformationFragment : Fragment() {

    private lateinit var binding: FragmentDeveloperInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDeveloperInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUnderLine()

        binding.tvViber.setOnClickListener {
            val phoneNo = binding.tvViber.text.toString()
            val modifiedPhoneNo = replacePrefix(phoneNo)
            openViberApp(requireActivity(),modifiedPhoneNo)
        }

        binding.tvEmail.setOnClickListener {
            openEmailApp(requireActivity(), binding.tvEmail.text.toString())
        }

        binding.tvFacebook.setOnClickListener {
            openFacebookApp(requireActivity(),"https://www.facebook.com/profile.php?id=61552194935730&mibextid=LQQJ4d")
        }

    }

    fun setUnderLine(){
        setUnderLineForLinks(binding.tvEmail)
        setUnderLineForLinks(binding.tvFacebook)
        setUnderLineForLinks(binding.tvViber)

    }

}