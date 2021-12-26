package com.rsdev.myeventschedule.view.ui.activity

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.rsdev.myeventschedule.api.Status
import com.rsdev.myeventschedule.databinding.ActivityMainBinding
import com.rsdev.myeventschedule.view.ui.adapters.eventinfo.EventInfoAdapter
import com.rsdev.myeventschedule.view.ui.dialog.LoadingDialog
import com.rsdev.myeventschedule.view.ui.eventinfo.EventInfoViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: EventInfoViewModel

    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[EventInfoViewModel::class.java]

        binding.eventList.layoutManager = GridLayoutManager(this, 1)

        lifecycleScope.launch {
            viewModel.eventState.collect {
                when (it.status) {
                    Status.LOADING -> {
                        binding.eventApiError.visibility = View.GONE
                        dialog?.hide()
                        dialog = LoadingDialog(
                            this@MainActivity,
                            "Aguarde estamos carregando os eventos."
                        )
                        dialog?.show()
                    }
                    Status.ERROR -> {
                        dialog?.hide()
                        binding.eventApiError.visibility = View.VISIBLE
                        binding.eventApiError.text = it.message
                    }
                    Status.SUCCESS -> {
                        dialog?.hide()
                        binding.eventApiError.visibility = View.GONE
                        binding.eventList.adapter = EventInfoAdapter(it.data)
                    }
                    else -> {}
                }
            }
        }
    }
}