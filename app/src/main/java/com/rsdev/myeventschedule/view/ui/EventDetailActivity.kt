package com.rsdev.myeventschedule.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rsdev.myeventschedule.databinding.ActivityEventDetailBinding

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}