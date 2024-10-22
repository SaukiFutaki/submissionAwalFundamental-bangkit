package com.example.submissionawalnavdanapi.ui.finished

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.example.submissionawalnavdanapi.databinding.FragmentFinishedBinding
import com.example.submissionawalnavdanapi.ui.adapter.EventAdapterFinished

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    private lateinit var finishedViewModel: FinishedViewModel
    private lateinit var finishedAdapter: EventAdapterFinished

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val finishedViewModel =
            ViewModelProvider(this).get(FinishedViewModel::class.java)

        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        finishedAdapter = EventAdapterFinished()

        binding.recyclerViewFinished.layoutManager = GridLayoutManager(context,2)
        binding.recyclerViewFinished.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount = 2,
                spacing = 16,
                includeEdge = true
            )
        )


        binding.recyclerViewFinished.adapter = finishedAdapter

        finishedViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        finishedViewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            finishedAdapter.submitList(events)
        }

        finishedViewModel.error.observe(viewLifecycleOwner) { error ->
           error?.let {
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

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) {
                outRect.top = spacing
            }
            outRect.bottom = spacing
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }
}