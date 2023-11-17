package com.aristo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.aristo.Manager.SharedPreferenceManager
import com.aristo.R
import com.aristo.databinding.ActivitySignUpBinding
import com.aristo.model.Customer
import com.aristo.network.FirebaseApi
import com.aristo.utils.modifyPhoneNumber

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignUpBinding
    private var firebaseApi = FirebaseApi()
    private var customer = Customer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.btnSignUp.setOnClickListener {

            if (validateFields()) {
                binding.progressBar.visibility = View.VISIBLE
                var found = false

                firebaseApi.getUser(customer.phone) { message, user ->
                    if (!found) {
                        found = true
                        if (user != null) {
                            binding.tvError.visibility = View.VISIBLE
                            Toast.makeText(this, "Phone Number is already registered", Toast.LENGTH_SHORT).show()
                        } else {
                            firebaseApi.signUpUser(customer) { successSignUp ->
                                if (successSignUp) {
                                    SharedPreferenceManager.initialize(this)
                                    SharedPreferenceManager.setData("UserLogIn", customer.phone)

                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, "Failed to Sign Up", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun validateFields() : Boolean{
        val phoneNo = modifyPhoneNumber(binding.etPhoneNumber.text.toString())
        val userName = binding.etUserName.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        val address = binding.etAddress.text.toString()

        return if (userName.isEmpty() || phoneNo.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please enter User Name, Phone Number, Password and Address", Toast.LENGTH_SHORT).show()
            false
        } else if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            false
        } else if (password.length < 6) {
            Toast.makeText(this, "Password is too short", Toast.LENGTH_SHORT).show()
            false
        } else if (phoneNo.length < 10 || phoneNo.length > 16) {
            Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show()
            false
        } else {
            customer = Customer(userName = userName, phone = phoneNo, password = password, address = address)
            true
        }
    }

}