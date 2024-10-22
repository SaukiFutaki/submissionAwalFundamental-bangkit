package com.example.submissionawalnavdanapi.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawalnavdanapi.databinding.FragmentHomeBinding
import com.example.submissionawalnavdanapi.ui.adapter.EventAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var upcomingAdapter: EventAdapter
    private lateinit var finishedAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        upcomingAdapter = EventAdapter()
        finishedAdapter = EventAdapter()


        binding.recyclerViewUpcoming.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewUpcoming.adapter = upcomingAdapter

        binding.recyclerViewFinished.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFinished.adapter = finishedAdapter

        homeViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            upcomingAdapter.submitList(events.take(5))
        }

        homeViewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            finishedAdapter.submitList(events.take(5))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
