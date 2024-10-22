package com.example.submissionawalnavdanapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.submissionawalnavdanapi.data.response.ListEventsItem
import com.example.submissionawalnavdanapi.databinding.ItemFinishedEventBinding



class EventAdapterFinished : ListAdapter<ListEventsItem, EventAdapterFinished.EventViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemFinishedEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventAdapterFinished.EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class EventViewHolder(private val binding: ItemFinishedEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.tvEventTitleFinished.text = event.name
        }

    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }
        }

    }

}