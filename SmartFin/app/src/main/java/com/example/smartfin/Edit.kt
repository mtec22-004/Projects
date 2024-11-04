package com.example.smartfin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Edit: AppCompatActivity() {
    private lateinit var dbRegistration: DBRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile)

        dbRegistration = DBRegistration(this)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val userNameEditText = findViewById<EditText>(R.id.userNameEditText)


        // Retrieve the current username from the database and display it
        val currentUsername = dbRegistration.getLoggedInUsername()
        userNameEditText.setText(currentUsername)

        saveButton.setOnClickListener {
            // Get the new username entered by the user
            val newUsername = userNameEditText.text.toString().trim()

            // Update the username in the database
            if (newUsername.isNotEmpty()) {
                // Assuming you have a method in DBRegistration to update the username
                val updated = dbRegistration.updateUsername(newUsername)
                if (updated) {
                    // Display a success message
                    Toast.makeText(this, "Username updated successfully", Toast.LENGTH_SHORT).show()
                    // Navigate back to the dashboard activity
                    startActivity(Intent(this, Dashboard::class.java))
                    finish() // Finish the current activity to prevent going back to it with the back button
                } else {
                    // Display an error message
                    Toast.makeText(this, "Failed to update username", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Username field is empty, display a message to the user
                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
    }
