package com.rsdev.myeventschedule.view.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.rsdev.myeventschedule.api.Status
import com.rsdev.myeventschedule.databinding.ActivityMainBinding
import com.rsdev.myeventschedule.view.ui.adapters.eventinfo.EventInfoAdapter
import com.rsdev.myeventschedule.view.ui.eventinfo.EventInfoViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: EventInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[EventInfoViewModel::class.java]

        binding.eventList.layoutManager = GridLayoutManager(this, 1)
        binding.eventList.adapter = EventInfoAdapter(null)

        lifecycleScope.launch {
            viewModel.eventState.collect {
                when (it.status) {
                    Status.LOADING -> binding.eventProgressIndicator.visibility = View.VISIBLE
                    Status.ERROR -> {
                        binding.eventProgressIndicator.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        binding.eventProgressIndicator.visibility = View.GONE
                        binding.eventList.adapter = EventInfoAdapter(it.data)
                    }
                }
            }
        }
    }
}