package com.aristo.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.aristo.R
import com.aristo.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes

const val TOPIC = "/topics/myTopic2"
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        AppCenter.start(
            application, "9e748b4f-85ad-454e-a939-60a8889e7808\"",
            Analytics::class.java, Crashes::class.java
        )

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    // Handle the token here (e.g., send it to your server)
                    addUserDeviceToken(token)
                    Log.d("FCM Token", token)
                } else {
                    Log.e("FCM Token", "Failed to get FCM token")
                }
            }


        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
    }

    fun addUserDeviceToken(token : String){
        val databaseReference = FirebaseDatabase.getInstance().getReference("Tokens")

        // Check if the key is not null before adding the data
        databaseReference.child(token).child("token").setValue(token)
            .addOnSuccessListener {
                // Data successfully added to the database
                Log.d("Firebase", "Token added successfully")
            }
            .addOnFailureListener { e ->
                // Failed to add token to the database
                Log.w("Firebase", "Error adding token to database", e)
            }


    }

}