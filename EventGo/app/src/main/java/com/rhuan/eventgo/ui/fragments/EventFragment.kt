package com.rhuan.eventgo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.rhuan.eventgo.R
import com.rhuan.eventgo.databinding.FragmentEventBinding
import com.rhuan.eventgo.domain.response.Event
import com.rhuan.eventgo.domain.response.Places
import com.rhuan.eventgo.ui.dialog.CheckInDialogFragment
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
        id?.let {
            viewModel.getEvent(it)
            setupButton(it)
        }

        setupObservers()
        return binding.root
    }

    private fun setupButton(id: String) {
        binding.checkInButton.setOnClickListener {
            Log.i("checkIn", "checkIn")
            val dialogFragment = CheckInDialogFragment(id)
            dialogFragment.show(childFragmentManager, CheckInDialogFragment.DIALOG_FRAGMENT_KEY)
        }
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

            setupMap(Places(LatLng(event.latitude, event.longitude)))
        }
    }

    private fun setupMap(eventPlace: Places) {
        val bounds = LatLngBounds.builder()

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_Fragment) as SupportMapFragment

        mapFragment.getMapAsync { googleMap ->
            addMarker(eventPlace, googleMap)

            googleMap.setOnMapLoadedCallback {
                bounds.include(eventPlace.latLng)

                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngBounds(
                        bounds.build(), 500
                    )
                )
                googleMap.animateCamera(
                    CameraUpdateFactory
                        .zoomTo(15f)
                )
            }
        }
    }

    private fun addMarker(place: Places, googleMap: GoogleMap) {
        val marker = googleMap.addMarker(
            MarkerOptions()
                .position(place.latLng)
        )
    }
}