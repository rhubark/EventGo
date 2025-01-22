package com.rhuan.eventgo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.rhuan.eventgo.R
import com.rhuan.eventgo.databinding.FragmentHomeBinding
import com.rhuan.eventgo.ui.adapters.EventsHomeAdapter
import com.rhuan.eventgo.ui.viewModels.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding
            .inflate(inflater, container, false)

        setupAdapter()
        setupObservers()
        viewModel.getAllEvents()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.loadingAllEvents.observe(viewLifecycleOwner) {}

        viewModel.fetchAllEventsResult.observe(viewLifecycleOwner) {
            binding.recyclerView
            adapterEvents.update(it)
        }
        viewModel.fetchAllEventsDataError.observe(viewLifecycleOwner) {}
    }

    private fun setupAdapter() {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterEvents
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }

    private val adapterEvents: EventsHomeAdapter by lazy {
        EventsHomeAdapter(object : EventsHomeAdapter.EventListener {
            override fun onEventClick(id: String) {
                findNavController().navigate(
                    R.id.action_home_fragment_to_eventFragment,
                    bundleOf("id" to id)
                )
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}