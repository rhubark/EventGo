package com.rhuan.eventgo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
import com.rhuan.eventgo.utils.Constants
import com.rhuan.eventgo.utils.DialogUtils
import com.rhuan.eventgo.utils.Formats
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventFragment : Fragment() {

    private val viewModel: EventFragmentViewModel by viewModel()
    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var event: Event

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupButton(id: String) {
        binding.checkInButton.setOnClickListener {
            val dialogFragment = CheckInDialogFragment(id)
            dialogFragment.show(childFragmentManager, Constants.DIALOG_FRAGMENT_KEY)
        }

        binding.shareButton.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT, String.format(
                        Constants.EVENT_SHARE_MESSAGE,
                        event.title,
                        Formats.longToDateExtensive(event.date),
                        Formats.money(event.price.toBigDecimal())
                    )
                )
                putExtra(
                    Intent.EXTRA_TITLE,
                    getString(R.string.share_event)
                )
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        binding.ivBtnBack.setOnClickListener {
            findNavController().popBackStack()

        }
    }

    private fun setupObservers() {
        viewModel.fetchEventResult().observe(viewLifecycleOwner) {
            event = it
            setupView(it)
        }

        viewModel.postError().observe(viewLifecycleOwner) {
            if (it) DialogUtils.alert(
                requireContext()
            ) { dialog, _ ->
                viewModel.postError.value = false
                dialog.dismiss()
            }
        }
    }

    private fun setupView(event: Event) {
        binding.apply {
            Picasso.get()
                .load(event.image)
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