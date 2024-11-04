package com.example.smartfin

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IncomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val incomeName: TextView = itemView.findViewById(R.id.Allowance) // Changed to lowercase
    private val incomeAmount: TextView = itemView.findViewById(R.id.income_money)
    private val deleteButton: ImageView = itemView.findViewById(R.id.income_delete)
    private val editButton: ImageView = itemView.findViewById(R.id.income_edit)

    fun bind(income: data_Income, listener: IncomeAdapter.OnItemClickListener) {
        incomeName.text = income.name
        incomeAmount.text = NumberUtils.formatNumber(income.amount)

        // Handle click events for delete button
        deleteButton.setOnClickListener {
            listener.onItemDeleteClick(income)
        }

        // Handle click events for edit button
        editButton.setOnClickListener {
            listener.onItemEditClick(income)
        }
    }
}
