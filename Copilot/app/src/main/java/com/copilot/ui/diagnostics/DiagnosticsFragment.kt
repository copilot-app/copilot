package com.copilot.ui.diagnostics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.copilot.data.model.Information
import com.copilot.databinding.FragmentDiagnosticsBinding

class DiagnosticsFragment : Fragment() {

    private var _binding: FragmentDiagnosticsBinding? = null
    private val binding get() = _binding!!
    private var mList = ArrayList<Information>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val diagnosticsViewModel =
            ViewModelProvider(this)[DiagnosticsViewModel::class.java]

        _binding = FragmentDiagnosticsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.errorsGroup
        diagnosticsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val adapter = InformationAdapter(mList)

        binding.recycleViewerOfInfo.apply {
            this.setHasFixedSize(true)
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }

        addDatatoList()

        return root
    }

    private fun addDatatoList() {
        mList.add(Information("Water temperature", "90"))
        mList.add(Information("Oil temperature", "60"))
        mList.add(Information("RPM", "900"))
        mList.add(Information("Fuel consumption", "5l / 100km"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}