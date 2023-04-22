package com.copilot.ui.diagnostics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.copilot.R
import com.copilot.data.model.ErrorMessage
import com.copilot.data.model.Information
import com.copilot.databinding.FragmentDiagnosticsBinding

class DiagnosticsFragment : Fragment() {

    private var _binding: FragmentDiagnosticsBinding? = null
    private val binding get() = _binding!!

    private var informationList = ArrayList<Information>()
    private var errorList = ArrayList<ErrorMessage>()

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

        val informationMessageAdapter = InformationMessageAdapter(informationList)

        binding.recycleViewerOfInfo.apply {
            this.setHasFixedSize(true)
            this.adapter = informationMessageAdapter
            layoutManager = LinearLayoutManager(context)
        }

        val errorAdapter = ErrorMessageAdapter(errorList)

        binding.recycleViewerOfErrors.apply {
            this.setHasFixedSize(true)
            this.adapter = errorAdapter
            layoutManager = LinearLayoutManager(context)
        }

        var isErrorsExpanded = false
        var isInformationExpanded = false

        var heightOfCardView = resources.getDimensionPixelSize(R.dimen.card_view_height)

        binding.btnExpandErrors.setOnClickListener {
            if (!isErrorsExpanded) {
                binding.recycleViewerOfErrors.layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, heightOfCardView * 4)
                binding.recycleViewerOfInfo.layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, heightOfCardView)

                binding.btnExpandErrors.setIconResource(R.drawable.ic_arrow_up_24)

                if (isInformationExpanded) {
                    binding.btnExpandGeneralInformation.setIconResource(R.drawable.ic_arrow_down_24)
                    isInformationExpanded = false
                }
                isErrorsExpanded = true
            } else {
                binding.recycleViewerOfErrors.layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, heightOfCardView)
                binding.btnExpandErrors.setIconResource(R.drawable.ic_arrow_down_24)

                isErrorsExpanded = false
            }
        }

        binding.btnExpandGeneralInformation.setOnClickListener {
            if (!isInformationExpanded) {
                binding.recycleViewerOfInfo.layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, heightOfCardView * 4)
                binding.recycleViewerOfErrors.layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, heightOfCardView)

                binding.btnExpandGeneralInformation.setIconResource(R.drawable.ic_arrow_up_24)

                if (isErrorsExpanded) {
                    binding.btnExpandErrors.setIconResource(R.drawable.ic_arrow_down_24)
                    isErrorsExpanded = false
                }

                isInformationExpanded = true
            } else {
                binding.recycleViewerOfInfo.layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, heightOfCardView)
                binding.btnExpandGeneralInformation.setIconResource(R.drawable.ic_arrow_down_24)

                isInformationExpanded = false
            }
        }

        addDatatoLists()

        return root
    }

    private fun addDatatoLists() {
        informationList.add(Information("Water temperature", "90"))
        informationList.add(Information("Oil temperature", "60"))
        informationList.add(Information("RPM", "900"))
        informationList.add(Information("Fuel consumption", "5l / 100km"))

        errorList.add(ErrorMessage("Fuel Delivery Error", "P0148"))
        errorList.add(ErrorMessage("Clutch Position Control Error", "P0810"))
        errorList.add(ErrorMessage("Exhaust Pressure Sensor Low", "P0472"))
        errorList.add(ErrorMessage("Control Module Programming Error", "P0602"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}