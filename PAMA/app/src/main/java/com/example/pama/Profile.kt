package com.example.pama

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class Profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userprofile)
        val btn = findViewById<Switch>(R.id.switch2)
        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val phoneNumberTextView = findViewById<TextView>(R.id.phoneNumberTextView)
        val editProfileButton = findViewById<Button>(R.id.editProfileButton)

        // Retrieve saved preferences
        val sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)
        val savedName = sharedPreferences.getString("preferredName", "")
        val savedNumber = sharedPreferences.getString("editableNumber", "")


        // Display saved preferences
        nameTextView.text = savedName
        phoneNumberTextView.text = savedNumber

        editProfileButton.setOnClickListener {
            // Create an Intent to start the "ProfileEdit" activity
            val intent = Intent(this, Edit::class.java)
            startActivity(intent)
        }
        btn.setOnCheckedChangeListener { _, isChecked ->
            if (btn.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                btn.text = "Disable dark mode"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                btn.text = "Enable dark mode"
            }
        }
    }


}
