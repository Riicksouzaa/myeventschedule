package com.rsdev.myeventschedule.view.ui.activity

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.rsdev.myeventschedule.R
import com.rsdev.myeventschedule.api.EventsApiState
import com.rsdev.myeventschedule.api.Status
import com.rsdev.myeventschedule.databinding.ActivityEventDetailBinding
import com.rsdev.myeventschedule.model.event.Event
import com.rsdev.myeventschedule.utils.highorder.formatToDate
import com.rsdev.myeventschedule.utils.highorder.transformToFiatBRL
import com.rsdev.myeventschedule.view.ui.adapters.eventinfo.EXTRA_EVENT_ID
import com.rsdev.myeventschedule.view.ui.dialog.CheckInDialog
import com.rsdev.myeventschedule.view.ui.dialog.LoadingDialog
import com.rsdev.myeventschedule.view.ui.eventdetail.EventDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding
    private lateinit var viewModel: EventDetailViewModel

    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this)[EventDetailViewModel::class.java]

        val eventFromParcel: Event = intent.getSerializableExtra(EXTRA_EVENT_ID) as Event
        viewModel.getEventById(eventFromParcel.id.toInt())

        setupEventDetail(eventFromParcel)

        lifecycleScope.launch {
            viewModel.eventState.collect {
                when (it.status) {
                    Status.LOADING -> {}
                    Status.ERROR -> {}
                    Status.SUCCESS -> {
                        it.data?.let { event -> setupEventDetail(event) }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun collectCheckInState(eventsApiState: EventsApiState<Event>) {
        when (eventsApiState.status) {
            Status.LOADING -> {
                dialog?.hide()
                dialog = LoadingDialog(this, eventsApiState.message.toString())
                dialog?.show()
            }
            Status.ERROR -> {
                dialog?.hide()
                Toast.makeText(
                    this@EventDetailActivity,
                    eventsApiState.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            Status.SUCCESS -> {
                dialog?.hide()
                Toast.makeText(
                    this@EventDetailActivity,
                    "checkIn Realizado Com Sucesso",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {}
        }
    }

    private fun setupEventDetail(event: Event) {
        Picasso.get().load(event.image)
            .placeholder(R.drawable.ic_no_image_placeholder)
            .into(binding.eventDetailImage)
        binding.eventDetailDescription.text = event.description
        binding.eventDetailCollapsingToolbarLayout.title = event.title
        binding.eventDetailDate.text = event.date.formatToDate("dd/MM/yyyy HH:mm")
        binding.eventDetailPrice.text = event.price.transformToFiatBRL()

        setupClickListeners(event)
    }

    private fun setupClickListeners(event: Event) {
        binding.eventDetailGotoLocation.setOnClickListener {
            val gmmIntentUri =
                Uri.parse("google.streetview:cbll=${event.latitude},${event.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        binding.eventDetailSendCheckIn.setOnClickListener {
            dialog = CheckInDialog(this) { name, email ->
                lifecycleScope.launch {
                    viewModel.checkIn(event, name, email)
                    lifecycleScope.launchWhenCreated {
                        viewModel.checkInState.collect {
                            collectCheckInState(it)
                        }
                    }
                }
            }
            dialog?.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        else -> super.onOptionsItemSelected(item)
    }
}