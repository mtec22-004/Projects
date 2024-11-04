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

class Income : AppCompatActivity(), IncomeAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: IncomeAdapter
    private lateinit var toolbarBack: ImageView
    private lateinit var fabAddIncome: ExtendedFloatingActionButton
    private lateinit var dbHelper: DBHelper
    private lateinit var totalInputAmountTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.income)
        dbHelper = DBHelper(this)
        initializeViews()
        setupListeners()
        setupRecyclerView()
        loadIncomeData()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = IncomeAdapter(emptyList(), this)
        recyclerView.adapter = adapter
    }

    private fun loadIncomeData() {
        val incomeList = dbHelper.getAllIncomes()
        adapter.updateData(incomeList)

        val totalInputAmount = incomeList.sumOf { it.amount }
        totalInputAmountTextView.text = getString(R.string.total_expenses_format, totalInputAmount)
    }

    private fun showAddIncomeDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialogue_add_income, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.editTextCategory)
        val amountEditText = dialogView.findViewById<EditText>(R.id.editTextAmount)

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add Income")
            .setPositiveButton("Save") { dialog, _ ->
                val name = nameEditText.text.toString()
                val amountStr = amountEditText.text.toString()
                if (name.isNotEmpty() && amountStr.isNotEmpty()) {
                    val amount = amountStr.toDoubleOrNull()
                    if (amount != null) {
                        val newIncome = data_Income(0, name, amount)  // 0 as temporary ID, real ID is auto-generated by the database
                        dbHelper.insertIncome(newIncome)
                        loadIncomeData()
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

    override fun onItemClick(income: data_Income) {
        showEditIncomeDialog(income)
    }

    private fun showEditIncomeDialog(income: data_Income) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialogue_add_income, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.editTextCategory)
        val amountEditText = dialogView.findViewById<EditText>(R.id.editTextAmount)
        nameEditText.setText(income.name)
        amountEditText.setText(income.amount.toString())

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Edit Income")
            .setPositiveButton("Save") { dialog, _ ->
                val name = nameEditText.text.toString()
                val amountStr = amountEditText.text.toString()
                if (name.isNotEmpty() && amountStr.isNotEmpty()) {
                    val amount = amountStr.toDoubleOrNull()
                    if (amount != null) {
                        val updatedIncome = data_Income(income.id, name, amount)
                        dbHelper.updateIncome(updatedIncome)
                        loadIncomeData()
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

    override fun onItemDeleteClick(income: data_Income) {
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Delete Income")
            .setMessage("Are you sure you want to delete this income entry?")
            .setPositiveButton("Delete") { dialog, _ ->
                dbHelper.deleteIncome(income.id)
                loadIncomeData()
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

    override fun onItemEditClick(income: data_Income) {
          showEditIncomeDialog(income)
    }

    private fun initializeViews() {
        toolbarBack = findViewById(R.id.toolbar_back)
        fabAddIncome = findViewById(R.id.fab_add_income)
        totalInputAmountTextView = findViewById(R.id.income_total)
    }

    private fun setupListeners() {
        toolbarBack.setOnClickListener {
            navigateToDashboard()
        }
        fabAddIncome.setOnClickListener {
            showAddIncomeDialog()
        }
    }

    private fun navigateToDashboard() {
        val intent = Intent(this, Dashboard::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
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