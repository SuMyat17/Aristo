package com.aristo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aristo.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}