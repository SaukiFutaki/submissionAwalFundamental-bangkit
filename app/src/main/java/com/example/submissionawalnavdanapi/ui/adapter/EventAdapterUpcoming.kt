package com.example.submissionawalnavdanapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionawalnavdanapi.data.response.ListEventsItem
import com.example.submissionawalnavdanapi.databinding.ItemUpcomingEventBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EventAdapterUpcoming(private val onItemClick: (ListEventsItem) -> Unit) : ListAdapter<ListEventsItem, EventAdapterUpcoming.EventViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {

        val binding = ItemUpcomingEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)

        holder.itemView.setOnClickListener {
            onItemClick(event)
        }
    }



    class EventViewHolder(private val binding: ItemUpcomingEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.tvEventName.text = event.name
//            binding.tvCityName.text = "Lokasi: ${event.cityName}"
//            binding.tvQuota.text = "Quota: ${event.quota}"
//            val formattedDate = formatDateTime(convertDateTime(event.beginTime))
//            binding.tvEventDate.text = "Tanggal: $formattedDate"
            Glide.with(itemView.context)
                .load(event.imageLogo)
                .into(binding.imageEvent)
        }

        private fun convertDateTime(dateTime: String): LocalDateTime {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return LocalDateTime.parse(dateTime, formatter)
        }


        private fun formatDateTime(dateTime: LocalDateTime): String {
            return "${dateTime.dayOfMonth} ${dateTime.month} ${dateTime.year}"
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem.id == newItem.id // Use ID for comparison
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem // Compare contents
            }
        }
    }


}
