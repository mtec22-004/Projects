package com.example.smartfin

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
   private val expenseName: TextView = itemView.findViewById(R.id.School)
    private val expenseAmount: TextView = itemView.findViewById(R.id.bling)
    private val deleteButton : ImageView = itemView.findViewById(R.id.expense_delete)
    private val editButton: ImageView= itemView.findViewById(R.id.expense_edit)

    fun bind(expense: dataExpenses, listener: Expenses) {
        expenseName.text = expense.name
        expenseAmount.text = NumberUtils.formatNumber(expense.amount)
        deleteButton.setOnClickListener {
            listener.onItemDeleteClick(expense)
        }
        editButton.setOnClickListener {
            listener.onItemEditClick(expense)
        }
    }

}
