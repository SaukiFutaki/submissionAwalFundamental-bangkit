package com.example.submissionawalnavdanapi.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawalnavdanapi.databinding.FragmentUpcomingBinding
import com.example.submissionawalnavdanapi.ui.adapter.EventAdapterUpcoming

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var upcomingAdapter: EventAdapterUpcoming
    private lateinit var upcomingViewModel: UpcomingViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        upcomingViewModel = ViewModelProvider(this).get(UpcomingViewModel::class.java)

        val upcomingViewModel =
            ViewModelProvider(this).get(UpcomingViewModel::class.java)

        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        upcomingAdapter = EventAdapterUpcoming()

        binding.recyclerViewUpcoming.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewUpcoming.adapter = upcomingAdapter


        upcomingViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            // Update the adapter with the upcoming events
            upcomingAdapter.submitList(events)
        }

        upcomingViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            // Show or hide loading indicator based on isLoading
            if (isLoading) {
                // Show loading indicator
                binding.progressBar.visibility = View.VISIBLE
            } else {
                // Hide loading indicator
                binding.progressBar.visibility = View.GONE
            }
        }

        upcomingViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            // Show error message (e.g., Toast or Snackbar)
            // You can replace this with your error handling logic
            errorMessage?.let {
                // Show a Toast message (optional)
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}