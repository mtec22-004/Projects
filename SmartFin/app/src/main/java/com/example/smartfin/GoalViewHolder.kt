package com.example.smartfin

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.LinearProgressIndicator

class GoalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
   private val titleTextView: TextView = view.findViewById(R.id.new_car_title)
   private var amountTextView: TextView = view.findViewById(R.id.new_car_amount)
    private val progressBar: LinearProgressIndicator = view.findViewById(R.id.new_car_progress)
    private val editButton : ImageView = itemView.findViewById(R.id.goals_edit)
    private val deleteButton: ImageView = itemView.findViewById(R.id.goals_delete)

    @SuppressLint("SetTextI18n")
    fun bind (goal: data_Goal, listener: GoalsAdapter.OnItemClickListener) {
        titleTextView.text = goal.title
        progressBar.progress = goal.progress
        amountTextView.text =  "${NumberUtils.formatNumber(goal.currentAmount)} / ${NumberUtils.formatNumber(goal.targetAmount)}"

        editButton.setOnClickListener {
            listener.onItemEditClick(goal)
        }
        deleteButton.setOnClickListener {
            listener.onItemDeleteClick(goal)
        }



    }

}
