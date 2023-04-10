package com.copilot.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.copilot.R
import com.copilot.databinding.FragmentDashboardBinding
import com.github.anastr.speedviewlib.PointerSpeedometer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DashboardFragment : Fragment(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var dashboardViewModel: DashboardViewModel

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val speed: PointerSpeedometer = binding.pointerSpeedometer
        dashboardViewModel.speed.observe(viewLifecycleOwner) {
            speed.speedTo(it.toFloat())
        }

        val fuel: PointerSpeedometer = binding.pointerFuelmeter
        dashboardViewModel.fuel.observe(viewLifecycleOwner) {
            fuel.speedTo(it.toFloat())
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        dashboardViewModel.location.observe(viewLifecycleOwner) {
            map.addMarker(MarkerOptions().position(it))
            map.moveCamera(CameraUpdateFactory.newLatLng(it))
        }
    }
}