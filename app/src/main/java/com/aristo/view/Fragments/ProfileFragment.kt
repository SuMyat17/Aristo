package com.aristo.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aristo.Manager.SharedPreferenceManager
import com.aristo.databinding.FragmentProfileBinding
import com.aristo.model.Customer
import com.aristo.network.FirebaseApi

class ProfileFragment: Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private var firebaseApi = FirebaseApi()
    private var loggedInUser : Customer? = null
    private var isEdit = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SharedPreferenceManager.initialize(requireContext())
        val phone = SharedPreferenceManager.getData("UserLogIn")

        phone?.let {
            firebaseApi.getUser(phone) { message, user ->
                if (user != null) {
                    loggedInUser = user
                    binding.etName.setText(user.userName)
                    binding.etPhoneNumber.setText(user.phone)
                    binding.etUserId.setText(user.userId.toString())
                    binding.etAddress.setText(user.address)
                    binding.tvPoints.text = "Points :  ${user.point}"
                } else {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnSave.setOnClickListener {
            loggedInUser?.let {
                it.userName = binding.etName.text.toString()
                it.address = binding.etAddress.text.toString()
                binding.progressBar.visibility = View.VISIBLE

                firebaseApi.updateUser(loggedInUser!!) { isSuccess ->
                    if (isSuccess) {
                        makeViewsUnClickable()
                        Toast.makeText(requireContext(), "Update Successful", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Failed to update", Toast.LENGTH_SHORT).show()
                    }
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        binding.btnEdit.setOnClickListener {
            if (!isEdit) {
                makeViewsClickable()
            } else {
                makeViewsUnClickable()
            }
        }
    }

    private fun makeViewsUnClickable() {
        isEdit = false
        binding.btnSave.visibility = View.GONE
        binding.btnEdit.text = "Edit"
        binding.etName.isFocusable = false
        binding.etAddress.isFocusable = false
    }

    private fun makeViewsClickable() {
        isEdit = true
        binding.btnSave.visibility = View.VISIBLE
        binding.btnEdit.text = "Cancel"
        binding.etName.isFocusable = true
        binding.etName.isFocusableInTouchMode = true
        binding.etAddress.isFocusable = true
        binding.etAddress.isFocusableInTouchMode = true
    }

}