package com.rsdev.myeventschedule.view.ui.adapters.eventinfo

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.rsdev.myeventschedule.R
import com.rsdev.myeventschedule.databinding.EventInfoListViewItemBinding
import com.rsdev.myeventschedule.model.event.Event
import com.rsdev.myeventschedule.view.ui.activity.EventDetailActivity
import com.squareup.picasso.Picasso

const val EXTRA_EVENT_ID = "extra_event_id"

class EventInfoViewHolder(
    private val binding: EventInfoListViewItemBinding,
    private val context: Context
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(event: Event) {
        binding.eventCardView.setOnClickListener {
            val intent = Intent(context.applicationContext, EventDetailActivity::class.java).apply {
                putExtra(EXTRA_EVENT_ID, event)
            }
            context.startActivity(intent)
        }
        with(event) {
            binding.eventTitle.text = title
            binding.eventDateDay.text = getDay()
            binding.eventDateMonth.text = getMonth()
            Picasso.get().load(image)
                .placeholder(R.drawable.ic_no_image_placeholder)
                .into(binding.eventImage)
        }
    }

    companion object {
        fun create(
            databinding: EventInfoListViewItemBinding,
            context: Context
        ): EventInfoViewHolder =
            EventInfoViewHolder(databinding, context)
    }
}