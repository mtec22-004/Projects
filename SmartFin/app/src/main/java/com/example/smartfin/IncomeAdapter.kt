package com.example.smartfin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class IncomeAdapter(private var incomes: List<data_Income>, private val listener: OnItemClickListener) : RecyclerView.Adapter<IncomeViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(income: data_Income)
        fun onItemDeleteClick(income: data_Income)
        fun onItemEditClick(income: data_Income)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_income, parent, false)
        return IncomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        val income = incomes[position]
        holder.bind(income, listener)
    }

    override fun getItemCount(): Int {
        return incomes.size
    }

    fun updateData(newIncomes: List<data_Income>) {
        incomes = newIncomes
        notifyDataSetChanged()
    }
}
