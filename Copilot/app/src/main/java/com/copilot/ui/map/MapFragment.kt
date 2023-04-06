package com.copilot.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.copilot.R
import com.copilot.databinding.FragmentMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var map: GoogleMap

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    var isMapExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mapViewModel =
            ViewModelProvider(this)[MapViewModel::class.java]

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupListeners()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    private fun setupListeners() {
        binding.resizeMapButton.setOnClickListener {
            isMapExpanded = !isMapExpanded
            binding.resizeMapButton.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    if (isMapExpanded)
                        R.drawable.ic_baseline_close_fullscreen_24
                    else
                        R.drawable.ic_baseline_open_in_full_24,
                    null
                )
            )
            binding.guideline.setGuidelinePercent(if (isMapExpanded) 0.75f else 0.5f)
        }
    }
}