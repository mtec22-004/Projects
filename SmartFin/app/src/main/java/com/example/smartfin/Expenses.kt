package com.example.smartfin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class Expenses : AppCompatActivity(), ExpenseAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpenseAdapter
    private lateinit var toolbarBack: ImageView
    private lateinit var fabAddExpenses : ExtendedFloatingActionButton
    private lateinit var dbExpHelper: DBExpHelper
    private lateinit var totalExpenseAmountTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expenses)

      setupRecyclerView()
        dbExpHelper = DBExpHelper(this)
        initializeViews()
        setupListeners()
        loadExpenseData()

        // Populate the expense list (you can fetch data from SQLite database here)
        loadExpenseData()
    }

    private fun setupListeners() {
      fabAddExpenses.setOnClickListener {
          showAddIncomeDialog()

      }
        toolbarBack.setOnClickListener {
            navigateToDashboard()
        }
    }

    private fun navigateToDashboard() {
        val intent = Intent(this, Dashboard::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }



    private fun showAddIncomeDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialogue_expenses, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add Expenses")
            .setPositiveButton("Save") { dialog, _ ->
                val name = dialogView.findViewById<EditText>(R.id.editTextExpenses).text.toString()
                val amountStr =
                    dialogView.findViewById<EditText>(R.id.editTextexpAmount).text.toString()
                if (name.isNotEmpty() && amountStr.isNotEmpty()) {
                    val amount = amountStr.toDoubleOrNull()
                    if (amount != null) {
                        val newExpense = dataExpenses(0,name, amount = amount)
                        dbExpHelper.insertExpenses(newExpense)
                        loadExpenseData()
                        updateDashboard()
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
    override fun onItemEditClick(expenses: dataExpenses) {
        showEditExpensesDialog(expenses)
    }

    private fun showEditExpensesDialog(expenses: dataExpenses) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialogue_add_income, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.editTextCategory)
        val amountEditText = dialogView.findViewById<EditText>(R.id.editTextAmount)
        nameEditText.setText(expenses.name)
        amountEditText.setText(expenses.amount.toString())

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Edit Expenses")
            .setPositiveButton("Save") { dialog, _ ->
                val name = nameEditText.text.toString()
                val amountStr = amountEditText.text.toString()
                if (name.isNotEmpty() && amountStr.isNotEmpty()) {
                    val amount = amountStr.toDoubleOrNull()
                    if (amount != null) {
                        val updatedExpenses = dataExpenses(expenses.id, name, amount)
                        dbExpHelper.updateExpenses(updatedExpenses)
                        loadExpenseData()
                        updateDashboard()
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




    override fun onItemClick(expense: data_Income) {
        TODO("Not yet implemented")
    }

    override fun onItemDeleteClick(expense: dataExpenses){
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Delete Expenses")
            .setMessage("Are you sure you want to delete this income entry?")
            .setPositiveButton("Delete") { dialog, _ ->
                dbExpHelper.deleteExpense(expense.id)
                loadExpenseData()
                updateDashboard()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialogBuilder.show()
    }


    private fun updateDashboard() {
        val intent = Intent(this, Dashboard::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
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


    private fun initializeViews() {
        toolbarBack = findViewById(R.id.toolbar_back)
        fabAddExpenses = findViewById(R.id.fab_add_expense)
        totalExpenseAmountTextView = findViewById(R.id.total_expense_amount)
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.expense_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExpenseAdapter(emptyList(), this)
        recyclerView.adapter = adapter
    }

    private fun loadExpenseData() {
        val expenseList = dbExpHelper.getAllExpenses()
       adapter.updateData(expenseList)

        val totalExpenseAmount = expenseList.sumOf { it.amount }
        totalExpenseAmountTextView.text = getString(R.string.total_expenses_format, totalExpenseAmount)

    }
}
