package com.copilot.ui.map

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.copilot.R
import com.copilot.databinding.FragmentMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var entryAdapter: EntryAdapter
    private lateinit var mapViewModel: MapViewModel

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private var isMapExpanded = false
    private val collapsedMapPercentageHeight = 0.4f
    private val expandedMapPercentageHeight = 0.6f
    private val collapseMapIcon = R.drawable.ic_baseline_close_fullscreen_24
    private val expandMapIcon = R.drawable.ic_baseline_open_in_full_24

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mapViewModel = ViewModelProvider(this)[MapViewModel::class.java]

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        entryAdapter = EntryAdapter()
        binding.entryList.apply {
            this.adapter = entryAdapter
            layoutManager = LinearLayoutManager(context)
        }
        mapViewModel.entryList.observe(viewLifecycleOwner) {
            entryAdapter.submitList(it)
        }

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
                    if (isMapExpanded) collapseMapIcon else expandMapIcon,
                    null
                )
            )
            val animator: ValueAnimator = ValueAnimator.ofFloat(
                if (isMapExpanded) collapsedMapPercentageHeight else expandedMapPercentageHeight,
                if (isMapExpanded) expandedMapPercentageHeight else collapsedMapPercentageHeight
            )
            animator.addUpdateListener { valueAnimator ->
                val value = valueAnimator.animatedValue as Float
                binding.guideline.setGuidelinePercent(value)
            }
            animator.start()
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val entry = entryAdapter.currentList[viewHolder.adapterPosition]
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.entryList)
    }
}