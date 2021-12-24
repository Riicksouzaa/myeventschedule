package com.rsdev.myeventschedule.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.rsdev.myeventschedule.api.Status
import com.rsdev.myeventschedule.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: EventsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(EventsViewModel::class.java)

        lifecycleScope.launch {
            viewModel.eventState.collect {
                when (it.status) {
                    Status.LOADING -> binding.heheBoy.text = "Loading"
                    Status.ERROR -> binding.heheBoy.text = it.message
                    Status.SUCCESS -> binding.heheBoy.text = "SUSEXO"
                }
            }
        }
    }
}