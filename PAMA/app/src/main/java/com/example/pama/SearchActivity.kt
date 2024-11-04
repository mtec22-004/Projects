package com.example.pama

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {

    private lateinit var editTextSearchQuery: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        editTextSearchQuery = findViewById(R.id.editTextSearchQuery)
        val buttonSearch: Button = findViewById(R.id.buttonSearch)

        buttonSearch.setOnClickListener {
            searchWeb()
        }
    }

    fun searchWeb() {
        val searchQuery = editTextSearchQuery.text.toString().trim()

        if (searchQuery.isNotEmpty()) {
            val uri = Uri.parse("https://www.google.com/search?q=$searchQuery")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
            // Show a "Search performed successfully" toast
            showToast("Search performed successfully")
        } else {
            // Show a "Please enter a search query" toast
            showToast("Please enter a search query")

        }
    }
    private fun showToast(message: String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }
}

