package com.copilot.ui.diagnostics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.copilot.R
import com.copilot.databinding.FragmentDiagnosticsBinding

class DiagnosticsFragment : Fragment() {
    private lateinit var diagnosticsViewModel: DiagnosticsViewModel
    private lateinit var errorMessageAdapter: ErrorMessageAdapter
    private lateinit var informationMessageAdapter: InformationMessageAdapter

    private var _binding: FragmentDiagnosticsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        diagnosticsViewModel = ViewModelProvider(this)[DiagnosticsViewModel::class.java]

        _binding = FragmentDiagnosticsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val errorAdapter = ErrorMessageAdapter()
        binding.recycleViewerOfErrors.apply {
            this.adapter = errorAdapter
            layoutManager = LinearLayoutManager(context)
        }

        diagnosticsViewModel.errorsList.observe(viewLifecycleOwner) {
            errorAdapter.submitList(it)
        }

        val informationAdapter = InformationMessageAdapter()
        binding.recycleViewerOfInfo.apply {
            this.adapter = informationAdapter
            layoutManager = LinearLayoutManager(context)
        }

        diagnosticsViewModel.informationList.observe(viewLifecycleOwner) {
            informationAdapter.submitList(it)
        }

        var isErrorsExpanded = false
        var isInformationExpanded = false

        val heightOfCardView = resources.getDimensionPixelSize(R.dimen.card_view_height)

        binding.btnExpandErrors.setOnClickListener {
            if (!isErrorsExpanded) {
                binding.recycleViewerOfErrors.layoutParams =
                    LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, heightOfCardView * 4)
                binding.recycleViewerOfInfo.layoutParams =
                    LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, heightOfCardView)

                binding.btnExpandErrors.setIconResource(R.drawable.ic_arrow_up_24)

                if (isInformationExpanded) {
                    binding.btnExpandGeneralInformation.setIconResource(R.drawable.ic_arrow_down_24)
                    isInformationExpanded = false
                }
                isErrorsExpanded = true
            } else {
                binding.recycleViewerOfErrors.layoutParams =
                    LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, heightOfCardView)
                binding.btnExpandErrors.setIconResource(R.drawable.ic_arrow_down_24)

                isErrorsExpanded = false
            }
        }

        binding.btnExpandGeneralInformation.setOnClickListener {
            if (!isInformationExpanded) {
                binding.recycleViewerOfInfo.layoutParams =
                    LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, heightOfCardView * 4)
                binding.recycleViewerOfErrors.layoutParams =
                    LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, heightOfCardView)

                binding.btnExpandGeneralInformation.setIconResource(R.drawable.ic_arrow_up_24)

                if (isErrorsExpanded) {
                    binding.btnExpandErrors.setIconResource(R.drawable.ic_arrow_down_24)
                    isErrorsExpanded = false
                }

                isInformationExpanded = true
            } else {
                binding.recycleViewerOfInfo.layoutParams =
                    LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, heightOfCardView)
                binding.btnExpandGeneralInformation.setIconResource(R.drawable.ic_arrow_down_24)

                isInformationExpanded = false
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
