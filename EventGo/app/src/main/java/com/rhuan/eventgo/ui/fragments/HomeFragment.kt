package com.rhuan.eventgo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.rhuan.eventgo.databinding.FragmentHomeBinding
import com.rhuan.eventgo.domain.response.Event
import com.rhuan.eventgo.ui.adapters.EventsHomeAdapter
import com.rhuan.eventgo.ui.viewholders.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val adapterEvents: EventsHomeAdapter by lazy { EventsHomeAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding
            .inflate(inflater, container, false)

        setupAdapter()
        setuoObservers()
        viewModel.getAllEvents()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setuoObservers() {
        viewModel.loadingEvent.observe(viewLifecycleOwner){}

        viewModel.fetchEventsResult.observe(viewLifecycleOwner){
            val evento: List<Event> = it
            binding.recyclerView
            adapterEvents.update(evento)
            Log.i("EVENTS", "aquiii ${evento[1]}")
        }

        viewModel.fetchEventDataError.observe(viewLifecycleOwner){}

    }


    private fun setupAdapter() {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterEvents
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }
}