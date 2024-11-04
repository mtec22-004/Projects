package com.example.smartfin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class Balance : AppCompatActivity() {
    private lateinit var toolbarBack: ImageView
    private lateinit var fabAddBalance: ExtendedFloatingActionButton
    private lateinit var dbHelper: BalanceDBHelper
    private lateinit var dbExpHelper: DBExpHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.budget)
        initializeViews()
        setupListeners()
        dbHelper = BalanceDBHelper(this)
        dbExpHelper = DBExpHelper(this)
        updateProgress()

        val balanceAmount = intent.getDoubleExtra("balance", 0.0)
        val balanceTextView = findViewById<TextView>(R.id.balance_total)
        balanceTextView.text = NumberUtils.formatNumber(balanceAmount)
    }

    private fun updateProgress() {
        val weeklyBudget = dbHelper.getweeklyBudget()
        val monthlyBudget = dbHelper.getMonthlyBudget()
        val totalExpenses = dbExpHelper.getAllExpenses()

        val weeklyProgress = calculateProgress(weeklyBudget, totalExpenses)
        val monthlyProgress = calculateProgress(monthlyBudget, totalExpenses)

        val progressBarWeekly = findViewById<ProgressBar>(R.id.progress_weekly_spending)
        val progressBarMonthly = findViewById<ProgressBar>(R.id.progress_monthly_spending)

        progressBarWeekly.progress = weeklyProgress
        progressBarMonthly.progress = monthlyProgress
    }

    private fun calculateProgress(budget: Double, expenses: List<dataExpenses>): Int {
        if (budget <= 0) return 0

        val totalExpenses = expenses.sumOf { it.amount }
        val progress = ((totalExpenses / budget) * 100).toInt()
        return progress.coerceIn(0, 100)
    }

    private fun setupListeners() {
        toolbarBack.setOnClickListener {
            navigateToDashboard()
        }

        fabAddBalance.setOnClickListener {
            showAddBalanceDialog()

        }
        val btnReset =  findViewById<Button>(R.id.btn_reset)
        btnReset.setOnClickListener { resetProgress()  }
    }
    private fun resetProgress() {
       dbHelper.resetBudgets()
        updateProgress()
        showToast("Budget and progress bars reset successful")

    }

    private fun navigateToDashboard() {
        val intent = Intent(this, Dashboard::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }

    private fun initializeViews() {
        toolbarBack = findViewById(R.id.toolbar_back)
        fabAddBalance = findViewById(R.id.fab_add_balance)
    }

    private fun showAddBalanceDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_budget, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add Balance")
            .setPositiveButton("Save") { dialog, _ ->
                val weeklyBudgetStr = dialogView.findViewById<EditText>(R.id.edit_text_weekly_budget).text.toString()
                val monthlyBudgetStr = dialogView.findViewById<EditText>(R.id.edit_text_monthly_budget).text.toString()

                if (weeklyBudgetStr.isNotEmpty() && monthlyBudgetStr.isNotEmpty()) {
                    val weeklyBudget = weeklyBudgetStr.toDoubleOrNull()
                    val monthlyBudget = monthlyBudgetStr.toDoubleOrNull()

                    if (weeklyBudget != null && monthlyBudget != null) {
                        dbHelper.addBudget(weeklyBudget, monthlyBudget)
                        showToast("Details added to the database")
                        updateProgress()
                    } else {
                        showErrorDialog("Invalid budget amount")
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
}
