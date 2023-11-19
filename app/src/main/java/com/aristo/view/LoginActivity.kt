package com.aristo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.aristo.Manager.SharedPreferenceManager
import com.aristo.R
import com.aristo.databinding.ActivityLoginBinding
import com.aristo.model.Customer
import com.aristo.network.FirebaseApi
import com.aristo.utils.modifyPhoneNumber

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    private var firebaseApi = FirebaseApi()
    private var customer = Customer()

    private var phoneNumber = ""
    private var password = ""

    override fun onStart() {
        super.onStart()

        SharedPreferenceManager.initialize(this)
        if (SharedPreferenceManager.getData("UserLogIn") != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else{
            if (firebaseApi.auth.currentUser == null) {
                firebaseApi.signInUser()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {

            if (validateFields()) {
                binding.progressBar.visibility = View.VISIBLE
                var found = false

                firebaseApi.getUser(phoneNumber) { message, user ->
                    if (!found) {
                        found = true
                        if (user != null) {
                            if (password == user.password) {
                                SharedPreferenceManager.setData("UserLogIn", user.phone)
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(this, "Wrong Phone Number or Password", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun validateFields() : Boolean{
        val phoneNo = modifyPhoneNumber(binding.etPhoneNumber.text.toString())
        val pass = binding.etPassword.text.toString()

        return if (phoneNo.length < 10 || phoneNo.length > 16) {
            Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show()
            false
        } else if (phoneNo.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please enter Phone Number, Password", Toast.LENGTH_SHORT).show()
            false
        } else if (pass.length < 6) {
            Toast.makeText(this, "Password is too short", Toast.LENGTH_SHORT).show()
            false
        } else {
            phoneNumber = phoneNo
            password = pass
            true
        }
    }
}