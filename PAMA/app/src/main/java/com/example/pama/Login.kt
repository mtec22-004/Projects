package com.example.pama
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Login : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        usernameEditText = findViewById(R.id.usernameEditText1)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.LoginButton1)


        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()


            if (checkCredentials(username, password)) {
                // Successful login, store a flag in SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.apply()

                // Start the next activity (e.g., DashboardActivity)
                val intent = Intent(this, Dashboard::class.java)
                intent.putExtra("username", username)


                startActivity(intent)
                finish() // Close the login activity
            } else {

                Toast.makeText(this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkCredentials(username: String, password: String): Boolean {
        // Replace this with your authentication logic
        return username == "Robby" && password == "Him"
    }
}
