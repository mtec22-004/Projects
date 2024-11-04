package com.example.pama

// MissedCallsAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MissedCallsAdapter(private val missedCallsList: List<MissedCall>) :
    RecyclerView.Adapter<MissedCallsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_missed_call, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val missedCall = missedCallsList[position]

        holder.callerName.text = missedCall.callerName
        holder.callTime.text = missedCall.callTime
    }

    override fun getItemCount(): Int {
        return missedCallsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val callerName: TextView = itemView.findViewById(R.id.textViewCallerName)
        val callTime: TextView = itemView.findViewById(R.id.textViewCallTime)
    }
}
