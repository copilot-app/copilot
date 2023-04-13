package com.copilot.ui.diagnostics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.copilot.R
import com.copilot.data.model.Information

class InformationAdapter(var mList: List<Information>) :
    RecyclerView.Adapter<InformationAdapter.InformationViewHolder>() {

    class InformationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val informationTitle : TextView = itemView.findViewById(R.id.information_title)
        val informationDescription : TextView = itemView.findViewById(R.id.information_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gen_info, parent, false)
        return InformationViewHolder(view)
    }

    override fun onBindViewHolder(holder: InformationViewHolder, position: Int) {
        holder.informationTitle.text = mList[position].title
        holder.informationDescription.text = mList[position].description
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}