package com.rhuan.eventgo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rhuan.eventgo.R
import com.rhuan.eventgo.databinding.FragmentEventBinding
import com.rhuan.eventgo.domain.response.Event
import com.rhuan.eventgo.ui.viewModels.EventFragmentViewModel
import com.rhuan.eventgo.utils.Formats
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel


class EventFragment : Fragment() {

    private val viewModel: EventFragmentViewModel by viewModel()
    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventBinding
            .inflate(inflater, container, false)

        val id = arguments?.getString("id")
        id?.let { viewModel.getEvent(it) }

        setupObservers()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.loadingEvent.observe(viewLifecycleOwner) {}

        viewModel.fetchEventResult.observe(viewLifecycleOwner) {
             setupView(it)
        }
        viewModel.fetchEventDataError.observe(viewLifecycleOwner) {}
    }

    private fun setupView(event: Event) {
        binding.apply {
            Picasso.get()
                .load(event.image)
                .placeholder(R.drawable.spaceship)
                .error(R.drawable.spaceship)
                .into(eventImage)

            eventTitle.text = event.title
            eventDate.text = Formats.longToDateExtensive(event.date)
            eventPrice.text = Formats.money(event.price.toBigDecimal())
            eventDescription.text = event.description
        }
    }



}