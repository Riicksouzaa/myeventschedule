package com.rsdev.myeventschedule.view.ui.adapters.eventinfo

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.rsdev.myeventschedule.R
import com.rsdev.myeventschedule.databinding.EventInfoListViewItemBinding
import com.rsdev.myeventschedule.model.event.Event
import com.rsdev.myeventschedule.view.ui.EventDetailActivity
import com.squareup.picasso.Picasso

class EventInfoViewHolder(
    private val binding: EventInfoListViewItemBinding,
    private val context: Context
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(event: Event) {
        binding.eventCardView.setOnClickListener {
            val intent = Intent(context.applicationContext, EventDetailActivity::class.java)
            context.startActivity(intent)
        }
        with(event) {
            binding.eventTitle.text = title
            binding.eventDateDay.text = getDay()
            binding.eventDateMonth.text = getMonth()
            Picasso.get().load(image)
                .placeholder(context.getDrawable(R.drawable.ic_launcher_background)!!)
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