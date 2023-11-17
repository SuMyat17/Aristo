package com.aristo.view

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aristo.R
import com.aristo.data.CartListDataHolder
import com.aristo.databinding.ActivityMainBinding
import com.aristo.view.Fragments.CartFragment
import com.aristo.view.Fragments.HomeFragment
import com.aristo.view.Fragments.InformationFragment
import com.aristo.view.Fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var doubleBackToExitPressedOnce = false

    lateinit var itemView : BottomNavigationItemView
    lateinit var badge: View
    lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemView = binding.bottomNav.findViewById(R.id.action_cart)
        badge = LayoutInflater.from(this).inflate(R.layout.badge_text, binding.bottomNav, false)
        text = badge.findViewById(R.id.tv_notification_badge)
        itemView.addView(badge)

        //rememberUser()

        when (intent.getStringExtra("fragmentToOpen")) {
            "Cart" -> {
                supportFragmentManager.beginTransaction().replace(R.id.fl_container, CartFragment()).commit()
                binding.bottomNav.selectedItemId = R.id.action_cart
            }
            else -> {
                supportFragmentManager.beginTransaction().replace(R.id.fl_container, HomeFragment()).commit()
                binding.bottomNav.selectedItemId = R.id.action_home
            }
        }

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> supportFragmentManager.beginTransaction().replace(R.id.fl_container, HomeFragment()).commit()
                R.id.action_cart -> supportFragmentManager.beginTransaction().replace(R.id.fl_container, CartFragment()).commit()
                R.id.action_information -> supportFragmentManager.beginTransaction().replace(R.id.fl_container, InformationFragment()).commit()
                R.id.action_profile -> supportFragmentManager.beginTransaction().replace(R.id.fl_container, ProfileFragment()).commit()
            }
            true
        }

        AppCenter.start(
            application, "9e748b4f-85ad-454e-a939-60a8889e7808\"",
            Analytics::class.java, Crashes::class.java
        )

    }

    override fun onResume() {
        super.onResume()
        showBadge()
    }

    fun showBadge() {
        if (CartListDataHolder.instance.cartList.size == 0) {
            text.visibility = View.GONE
        } else {
            text.visibility = View.VISIBLE
            text.text = CartListDataHolder.instance.cartList.size.toString()
        }
    }



    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()

//            SharedPreferenceManager.initializeSharedPref(this, "cartList")

            CartListDataHolder.instance.cartList?.clear()

            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000) // Delay for 2 seconds to reset the double tap flag
    }



}