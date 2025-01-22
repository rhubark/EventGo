package com.rhuan.eventgo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rhuan.eventgo.databinding.CardEventBinding
import com.rhuan.eventgo.domain.response.Event

class EventsHomeAdapter(
) : RecyclerView.Adapter<EventsHomeAdapter.ViewHolder>() {

    private var listEvents: List<Event> = emptyList()

    class ViewHolder(
        private val cardEventBinding: CardEventBinding
    ) :
        RecyclerView.ViewHolder(cardEventBinding.root) {

        fun bind(event: Event) {
            cardEventBinding.tvEventName.text = event.title
        }
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


        fun update(evento: List<Event>) {
           this.listEvents = evento

            notifyDataSetChanged()
        }



}