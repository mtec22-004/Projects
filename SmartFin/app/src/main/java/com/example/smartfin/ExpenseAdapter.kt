package com.example.smartfin
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView



class ExpenseAdapter(private var expenses: List<dataExpenses>, private val listener: Expenses) :
    RecyclerView.Adapter<ExpenseViewHolder>() {

        interface OnItemClickListener{
            fun onItemClick(expense: data_Income)
            fun onItemDeleteClick(expense: dataExpenses)
            fun onItemEditClick(expense: dataExpenses)

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_expenses, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.bind(expense, listener)
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    fun updateData(newExpenses: List<dataExpenses>) {
        expenses = newExpenses
        notifyDataSetChanged()

    }


}
