package com.rhuan.eventgo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.rhuan.eventgo.databinding.FragmentHomeBinding
import com.rhuan.eventgo.domain.response.Event
import com.rhuan.eventgo.service.RetrofitProvider
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Log.i("HomeFragment", "onCreate bug criado")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding
            .inflate(inflater, container, false)
        // Inflate the layout for this fragment


        lifecycleScope.launch(IO){
            Log.i("HomeFragment", "onCreate bug 2")

            val call: Call<List<Event>> = RetrofitProvider().eventService.getAllEvents()
            Log.i("HomeFragment", "onCreate bug 3")

            val response: Response<List<Event>> = call.execute()
            Log.i("HomeFragment", "onCreate bug 4")

            response.body()?.let { events ->
                Log.i("ListEvents", "onCreate ${events[1].date}")
            }
        }

        return binding.root
    }


}