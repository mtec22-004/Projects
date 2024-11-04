package com.example.smartfin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Dashboard : AppCompatActivity() {
    private lateinit var incomeTextView: TextView
    private lateinit var expenseTextView: TextView
    private lateinit var balanceTextView: TextView
    private lateinit var dbExpHelper: DBExpHelper
    private lateinit var dbHelper: DBHelper
    private lateinit var dbRegistration: DBRegistration
    private lateinit var userGreetingTextView: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        val detailsButton = findViewById<Button>(R.id.Details)
        val details1Button = findViewById<Button>(R.id.Details1)
        val details2Button = findViewById<Button>(R.id.Details2)
        val details6Button = findViewById<Button>(R.id.Details6)
        val toolbarMenuIcon = findViewById<ImageView>(R.id.toolbar_menu_icon)
        incomeTextView = findViewById(R.id.money)
        expenseTextView = findViewById(R.id.money3)
        balanceTextView = findViewById(R.id.money1)
        userGreetingTextView = findViewById(R.id.user)
        dbHelper = DBHelper(this)
        dbExpHelper = DBExpHelper(this)
        dbRegistration = DBRegistration(this)

       updateUsername()

        // Get all income items and calculate total income
        val incomeItems = dbHelper.getAllIncomes()
        val totalIncome = incomeItems.sumOf { it.amount }

// Get all expense items and calculate total expense
        val expenseItems = dbExpHelper.getAllExpenses()
        val totalExpense = expenseItems.sumOf { it.amount }

// Display total income and total expense
        incomeTextView.text = NumberUtils.formatNumber(totalIncome)
        expenseTextView.text = NumberUtils.formatNumber(totalExpense)

// Calculate balance
        val balance = totalIncome - totalExpense

// Display balance
        balanceTextView.text = NumberUtils.formatNumber(balance)

        detailsButton.setOnClickListener {
            val intent = Intent(this, Balance::class.java)
            intent.putExtra("balance",balance)
            startActivity(intent)
        }
        details1Button.setOnClickListener {
            val intent = Intent(this, Income::class.java)
            startActivity(intent)
        }
        details2Button.setOnClickListener {
            val intent = Intent(this, Expenses::class.java)
            startActivity(intent)
        }
        details6Button.setOnClickListener {
            val intent = Intent(this, Goals::class.java)
            startActivity(intent)
        }
        toolbarMenuIcon.setOnClickListener {
            showPopupMenu(it)
        }
    }

    private fun updateUsername() {
        val username = dbRegistration.getLoggedInUsername()
        userGreetingTextView.text = username ?: ""
    }

    private fun showPopupMenu(view: android.view.View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_dashboard, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_settings -> {
                    val intent = Intent(this, Settings::class.java)
                    startActivity(intent)
                    // Handle Settings menu item click
                    true
                }

                R.id.action_sign_out -> {
                    if (dbRegistration.signOutUser()) {
                        val intent = Intent(this, MainActivity ::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish() // Call this to finish all previous activities.
                    }
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}
