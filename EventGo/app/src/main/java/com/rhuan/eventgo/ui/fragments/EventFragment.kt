package com.rhuan.eventgo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rhuan.eventgo.databinding.FragmentEventBinding


class EventFragment : Fragment() {

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventBinding
            .inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

}