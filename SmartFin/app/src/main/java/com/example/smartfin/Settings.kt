package com.example.smartfin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Settings : AppCompatActivity() {
    private lateinit var dbRegistration: DBRegistration
    private lateinit var usernameTextView: TextView
    private lateinit var emailTextView: TextView

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the theme based on the saved preference
        if (ThemeUtils.loadTheme(this)) {
            setTheme(R.style.AppTheme_Dark)
        } else {
            setTheme(R.style.AppTheme_Light)
        }

        setContentView(R.layout.settings)
        dbRegistration = DBRegistration(this)

        // Initialize TextViews for username and email
        usernameTextView = findViewById(R.id.username_settings)
        emailTextView = findViewById(R.id.email_settings)

        // Fetch registered user data and display them
        displayUserData()

        val editButton = findViewById<Button>(R.id.edit_profile_button)
        editButton.setOnClickListener {
            val intent = Intent(this, Edit::class.java)
            startActivity(intent)
        }

        // Setup the switch
        val themeSwitch: Switch = findViewById(R.id.theme_switch)
        themeSwitch.isChecked = ThemeUtils.loadTheme(this)

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            ThemeUtils.saveTheme(this, isChecked)
            recreate()  // Restart the activity to apply the theme
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayUserData() {
        val currentUser = dbRegistration.getLoggedInUsername()
        if (currentUser != null) {
            // Assuming currentUser is the username string
            usernameTextView.text = currentUser
            // Assuming there's no separate method to get the email from the database
            // If there is, replace "currentUser" with the method call to get the email
            emailTextView.text = "user@example.com"  // Replace "user@example.com" with the actual email string
        }
    }

}

