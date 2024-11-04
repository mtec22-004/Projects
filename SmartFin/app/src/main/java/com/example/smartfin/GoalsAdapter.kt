package com.example.smartfin

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GoalsAdapter(private var goals: List<data_Goal>, private val listener: OnItemClickListener) : RecyclerView.Adapter<GoalViewHolder>() {
    interface OnItemClickListener {
        fun onItemClickListener(goal: data_Goal)
        fun onItemEditClick(goal: data_Goal)
        fun onItemDeleteClick(goal: data_Goal)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_budgetgoals, parent, false)
        return GoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = goals[position]
      holder.bind(goal, listener)


    }

    override fun getItemCount(): Int {
        return goals.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(goalsList: List<data_Goal>) {
        goals = goalsList
        notifyDataSetChanged()
    }
}
