package com.rsdev.myeventschedule.view.ui.adapters.eventinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rsdev.myeventschedule.databinding.EventInfoListViewItemBinding
import com.rsdev.myeventschedule.model.event.Event

class EventInfoAdapter(private val dataset: List<Event>?) :
    RecyclerView.Adapter<EventInfoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = EventInfoListViewItemBinding.inflate(inflater, parent, false)
        return EventInfoViewHolder.create(dataBinding, parent.context)
    }

    override fun onBindViewHolder(holder: EventInfoViewHolder, position: Int) {
        dataset?.let {
            holder.bind(dataset[position])
        }
    }

    override fun getItemCount(): Int = dataset?.size ?: 0
}