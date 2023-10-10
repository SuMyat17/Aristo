package com.aristo.view.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aristo.Manager.Network.Firebase
import com.aristo.Manager.openDialApp
import com.aristo.Manager.openFacebookApp
import com.aristo.Manager.openViberApp
import com.aristo.Manager.replacePrefix
import com.aristo.Manager.setUnderLineForLinks
import com.aristo.R
import com.aristo.databinding.FragmentInformationBinding
import com.aristo.databinding.FragmentShopInformationBinding

class ShopInformationFragment : Fragment() {

    private lateinit var binding: FragmentShopInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShopInformationBinding.inflate(inflater, container, false)

        binding.progressBar.visibility = View.VISIBLE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrieveShopDatas()

        binding.tvFacebook.setOnClickListener {
            openFacebookApp(requireActivity(),"https://www.facebook.com/appleauthorizedreseller?mibextid=ZbWKwL")
        }

        binding.tvPhoneNo.setOnClickListener {
            openDialApp(requireActivity(), binding.tvPhoneNo.text.toString())
        }

        binding.tvViber.setOnClickListener {
            val phoneNo = binding.tvViber.text.toString()
            val modifiedPhoneNo = replacePrefix(phoneNo)
            openViberApp(requireActivity(),modifiedPhoneNo)
        }
    }

    fun allViewHide(){
        binding.phoneLayout.visibility = View.GONE
        binding.facebookLayout.visibility = View.GONE
        binding.viberLayout.visibility = View.GONE
        binding.locationLayout.visibility = View.GONE
    }

    fun retrieveShopDatas(){
        Firebase.getShopDetail(requireActivity()){ isSuccess, data ->

            binding.progressBar.visibility = View.GONE

            if (isSuccess){

                if (data != null){

                    if (data.address == ""){
                        binding.locationLayout.visibility = View.GONE
                    }
                    else{
                        binding.locationLayout.visibility = View.VISIBLE
                        binding.tvAddress.setText(data.address)
                    }

                    if (data.viber == ""){
                        binding.viberLayout.visibility = View.GONE
                    }
                    else{
                        binding.viberLayout.visibility = View.VISIBLE
                        binding.tvViber.setText(data.viber)
                    }

                    if (data.phone == ""){
                        binding.phoneLayout.visibility = View.GONE
                    }
                    else{
                        binding.phoneLayout.visibility = View.VISIBLE
                        binding.tvPhoneNo.setText(data.phone)
                    }

                    if (data.fbPage == ""){
                        binding.facebookLayout.visibility = View.GONE
                    }
                    else{
                        binding.facebookLayout.visibility = View.VISIBLE
                        binding.tvFacebook.setText(data.fbPage)
                    }

                    setUnderLine()
                }
                else{
                    allViewHide()
                }
            }
            else{
                Log.d("Error is coming: ", "Error is coming: ")
            }

        }
    }

    fun setUnderLine(){
        setUnderLineForLinks(binding.tvPhoneNo)
        setUnderLineForLinks(binding.tvFacebook)
        setUnderLineForLinks(binding.tvViber)

    }
}