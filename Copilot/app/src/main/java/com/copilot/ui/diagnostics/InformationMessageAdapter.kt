package com.copilot.ui.diagnostics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.copilot.R
import com.copilot.data.model.ErrorMessage
import com.copilot.data.model.InformationMessage
import com.copilot.databinding.ItemMessageErrorBinding
import com.copilot.databinding.ItemMessageGeneralInfoBinding

class InformationMessageAdapter :
    ListAdapter<InformationMessage, InformationMessageAdapter.InformationViewHolder>(object : DiffUtil.ItemCallback<InformationMessage> () {
        override fun areItemsTheSame(
            oldItem: InformationMessage,
            newItem: InformationMessage
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: InformationMessage,
            newItem: InformationMessage
        ): Boolean {
            return oldItem.description == newItem.description
        }
    }) {

    class InformationViewHolder(private val binding: ItemMessageGeneralInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(informationMessage: InformationMessage) {
            binding.apply {
                binding.informationTitle.text = informationMessage.title
                binding.informationDescription.text = informationMessage.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationViewHolder {
        return InformationMessageAdapter.InformationViewHolder(
            ItemMessageGeneralInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: InformationViewHolder, position: Int) {
        val entry = getItem(position)
        holder.bind(entry)
    }
}
