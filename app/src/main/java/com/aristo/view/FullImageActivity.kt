package com.aristo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aristo.R
import com.aristo.databinding.ActivityFullImageBinding
import com.bumptech.glide.Glide

class FullImageActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFullImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullImageBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val imageUrl = intent.getStringExtra("image")
        Glide.with(this).load(imageUrl).into(binding.ivFullImage)

        binding.btnClose.setOnClickListener {
            finish()
        }
    }
}