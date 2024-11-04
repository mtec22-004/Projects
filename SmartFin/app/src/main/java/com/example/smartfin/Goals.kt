package com.example.smartfin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class Goals : AppCompatActivity(), GoalsAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GoalsAdapter
    private lateinit var fabAddGoals: ExtendedFloatingActionButton
    private lateinit var dbGoals: DBGoals
    private lateinit var toolbarBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.budget_goals)

        dbGoals = DBGoals(this)
        initializeViews()
        setupListeners()
        setupRecyclerView()
        loadGoalsData()
    }

    private fun loadGoalsData() {
        val goalsList = dbGoals.getAllGoals()
        adapter.updateData(goalsList)
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.goals_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = GoalsAdapter(emptyList(), this)
        recyclerView.adapter = adapter
    }

    private fun setupListeners() {
        toolbarBack = findViewById(R.id.toolbar_back)
        toolbarBack.setOnClickListener {
            navigateToDashboard()
        }
        fabAddGoals = findViewById(R.id.fab_add_goals)
        fabAddGoals.setOnClickListener {
            showAddGoalsDialog()
        }
    }

    private fun navigateToDashboard() {
        val intent = Intent(this, Dashboard::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }

    private fun showAddGoalsDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_goals, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Add Goals")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val title =
                    dialogView.findViewById<EditText>(R.id.edit_text_goal_title).text.toString()
                val targetAmountStr =
                    dialogView.findViewById<EditText>(R.id.edit_text_target_amount).text.toString()
                val targetAmount = targetAmountStr.toDoubleOrNull()

                if (title.isNotEmpty() && targetAmount != null) {
                    val newGoal = data_Goal(
                        0,
                        title = title,
                        progress = 0,
                        targetAmount = targetAmount,
                        currentAmount = 0.0
                    )
                    dbGoals.insertGoal(newGoal)
                    loadGoalsData()
                } else {
                    showErrorDialog("Please fill in all fields")
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialogBuilder.show()
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showEditGoalsDialog(goal: data_Goal) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.current, null)
        val currentEditText = dialogView.findViewById<EditText>(R.id.edit_text_current_amount)
        currentEditText.setText(goal.currentAmount.toString())

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Edit Goal")
            .setPositiveButton("Save") { dialog, _ ->
                val amountStr = currentEditText.text.toString()
                if (amountStr.isNotEmpty()) {
                    val currentAmount = amountStr.toDoubleOrNull()
                    if (currentAmount != null) {
                        val progress = (currentAmount / goal.targetAmount * 100).toInt()
                        val updatedGoal = data_Goal(goal.id, goal.title, progress, goal.targetAmount, currentAmount)
                        dbGoals.updateGoal(updatedGoal)
                        loadGoalsData()
                    } else {
                        showErrorDialog("Invalid amount")
                    }
                } else {
                    showErrorDialog("Please fill in all fields")
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialogBuilder.show()
    }

    private fun initializeViews() {
        fabAddGoals = findViewById(R.id.fab_add_goals)
        toolbarBack = findViewById(R.id.toolbar_back)
    }

    override fun onItemClickListener(goal: data_Goal) {
        // Handle item click
    }

    override fun onItemEditClick(goal: data_Goal) {
        showEditGoalsDialog(goal)
    }

    override fun onItemDeleteClick(goal: data_Goal) {
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Delete Goal")
            .setMessage("Are you sure you want to delete this goal entry?")
            .setPositiveButton("Delete") { dialog, _ ->
                dbGoals.deleteGoals(goal.id)
                loadGoalsData()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialogBuilder.show()
    }
}
