package com.copilot.ui.diagnostics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.copilot.data.model.ErrorMessage
import com.copilot.databinding.ItemMessageErrorBinding

class ErrorMessageAdapter :
    ListAdapter<ErrorMessage, ErrorMessageAdapter.ErrorViewHolder>(object : DiffUtil.ItemCallback<ErrorMessage>() {
        override fun areItemsTheSame(oldItem: ErrorMessage, newItem: ErrorMessage): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ErrorMessage, newItem: ErrorMessage): Boolean {
            return oldItem.code == newItem.code
        }
    }) {

     class ErrorViewHolder(private val binding: ItemMessageErrorBinding) : RecyclerView.ViewHolder(binding.root) {
         fun bind(errorMessage: ErrorMessage) {
             binding.apply {
                 binding.errorTitle.text = errorMessage.title
                 binding.errorCode.text = errorMessage.code
             }
         }
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ErrorViewHolder {
        return ErrorViewHolder(
            ItemMessageErrorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ErrorViewHolder, position: Int) {
        val entry = getItem(position)
        holder.bind(entry)
    }
}
