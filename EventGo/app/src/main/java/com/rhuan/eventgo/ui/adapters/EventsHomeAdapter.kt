package com.rhuan.eventgo.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.rhuan.eventgo.R
import com.rhuan.eventgo.databinding.CardEventBinding
import com.rhuan.eventgo.domain.response.Event
import com.rhuan.eventgo.utils.Formats
import com.squareup.picasso.Picasso

class EventsHomeAdapter(
    private val listener: EventListener
) : RecyclerView.Adapter<EventsHomeAdapter.ViewHolder>() {

    interface EventListener {
        fun onEventClick(id: String)
    }

    private var listEvents: List<Event> = emptyList()

    inner class ViewHolder(
        private val cardEventBinding: CardEventBinding
    ) :
        RecyclerView.ViewHolder(cardEventBinding.root) {

        fun bind(event: Event) {
            cardEventBinding.apply {
                tvDay.text = Formats.shortDate(event.date)
                tvMonth.text = Formats.longMonth(event.date)
                hour.text = Formats.longToHour(event.date)
                tvDistance.text = event.date.toString()

                configImage(cardEventBinding.ivEventImage, event)

                tvEventName.text = event.title
                root.setOnClickListener {
                    listener.onEventClick(event.id)
                }
            }
        }
    }

    private fun configImage(cardEventBinding: ImageView, event: Event) {
       Picasso.get()
           .load(event.image)
           .placeholder(R.drawable.spaceship)
           .error(R.drawable.spaceship)
           .into(cardEventBinding)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder(
            CardEventBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = listEvents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = listEvents[position]
        holder.bind(event)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(event: List<Event>) {
        this.listEvents = event
        notifyDataSetChanged()
    }
}