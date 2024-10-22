package com.example.submissionawalnavdanapi.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.submissionawalnavdanapi.databinding.FragmentDetailEventBinding
import com.example.submissionawalnavdanapi.data.response.ListEventsItem
import java.text.SimpleDateFormat
import java.util.Locale

class DetailEventFragment : Fragment() {

    private var _binding: FragmentDetailEventBinding? = null
    private val binding get() = _binding!!

    private lateinit var detailEventViewModel: DetailEventViewModel

    private fun formatDateTime(dateTimeString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())

            val date = inputFormat.parse(dateTimeString)
            date?.let { outputFormat.format(it) } ?: dateTimeString
        } catch (e: Exception) {
            dateTimeString
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailEventViewModel = ViewModelProvider(this).get(DetailEventViewModel::class.java)

        val args: DetailEventFragmentArgs by navArgs()
        val event = args.selectedEvent

        detailEventViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        event?.let {
            displayEventDetails(it)
        }

    }

    private fun displayEventDetails(event: ListEventsItem) {
        binding.tvEventName.text = event.name
        binding.tvEventLocation.text = "${event.cityName}"
       binding.tvEventTime.text = "${formatDateTime(event.beginTime)}"
        binding.tvEventCategory.text = "${event.category}"
        binding.tvEventQuota.text = "${event.registrants}/${event.quota}"
        binding.tvEventOwner.text = "${event.ownerName}"

        binding.tvEventDescription.text = HtmlCompat.fromHtml(
            event.description,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        Glide.with(this)
            .load(event.mediaCover)
            .into(binding.imageEventCover)

        binding.btnOpenEventLink.setOnClickListener {
            event.link?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
