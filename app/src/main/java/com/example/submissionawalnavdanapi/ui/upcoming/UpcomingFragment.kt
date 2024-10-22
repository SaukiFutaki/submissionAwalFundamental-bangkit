package com.example.submissionawalnavdanapi.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import com.example.submissionawalnavdanapi.data.response.ListEventsItem
import com.example.submissionawalnavdanapi.databinding.FragmentUpcomingBinding
import com.example.submissionawalnavdanapi.ui.adapter.EventAdapterUpcoming

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    private lateinit var upcomingAdapter: EventAdapterUpcoming
    private lateinit var upcomingViewModel: UpcomingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingViewModel = ViewModelProvider(this).get(UpcomingViewModel::class.java)

        setupRecyclerView()
        observeViewModel()


    }

    private fun setupRecyclerView() {
        upcomingAdapter = EventAdapterUpcoming { event ->
            navigateToDetailEvent(event)
        }
        binding.recyclerViewUpcoming.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = upcomingAdapter
        }
    }

    private fun observeViewModel() {
        upcomingViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            upcomingAdapter.submitList(events)
        }

        upcomingViewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        upcomingViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToDetailEvent(event: ListEventsItem) {
        val action = UpcomingFragmentDirections.actionNavigationUpcomingToNavigationDetailEvent(event)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
