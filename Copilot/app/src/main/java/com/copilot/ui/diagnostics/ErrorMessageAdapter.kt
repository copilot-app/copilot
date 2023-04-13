package com.copilot.ui.diagnostics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.copilot.R
import com.copilot.data.model.ErrorMessage

class ErrorMessageAdapter(var mList: ArrayList<ErrorMessage>) :
    RecyclerView.Adapter<ErrorMessageAdapter.ErrorViewHolder>() {

     class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val errorTitle : TextView = itemView.findViewById(R.id.error_title)
         val errorCode : TextView = itemView.findViewById(R.id.error_code)
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ErrorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_error, parent, false)
        return ErrorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ErrorViewHolder, position: Int) {
        holder.errorTitle.text = mList[position].title
        holder.errorCode.text = mList[position].code
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}
