package com.example.pama

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var phoneNumberEditText: EditText
    private lateinit var loginTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        // Initialize UI elements
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        phoneNumberEditText = findViewById(R.id.phoneNumberTextView)
        registerButton = findViewById(R.id.LoginButton)
        loginTextView = findViewById(R.id.loginTextView)

        // Set an OnClickListener for the register button
        registerButton.setOnClickListener {
            // Retrieve the entered username, email, and password
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()

            // Check if any of the fields are empty
            if (username.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
                // Show an error message using a Toast
                Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show()
            } else {
                // If all fields are filled, proceed with your registration logic
                if (isValidRegistration(username, password, phoneNumber)) {
                    // Save user information in shared preferences
                    saveUserInfo(username, phoneNumber)

                    // Registration successful, navigate to the next activity (e.g., DashboardActivity)
                    val intent = Intent(this, Dashboard::class.java)
                    intent.putExtra("username", username)
                    intent.putExtra("phoneNumber", phoneNumber)
                    startActivity(intent)
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                } else {
                    // Registration failed, handle accordingly (e.g., display a different error message)
                    Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show()
                }
            }
        }

        loginTextView.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    // Implement your own validation logic here
    private fun isValidRegistration(username: String, password: String, phoneNumber: String): Boolean {
        // Sample validation logic
        if (password.length < 8) {
            // Show a toast if the password is less than 8 characters
            Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
            return false
        }
        return username.isNotEmpty() && password.length >= 8 && isPhoneNumberValid(phoneNumber)

    }

    // Check if the phone number contains only digits
    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return phoneNumber.matches(Regex("^\\d+$"))
    }

    // Save user information in shared preferences
    private fun saveUserInfo(username: String, phoneNumber: String) {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("preferredName", username)
        editor.putString("editableNumber", phoneNumber)
        editor.apply()
    }
}
