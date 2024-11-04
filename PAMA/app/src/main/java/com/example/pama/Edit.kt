package com.example.pama

// ProfileEditActivity.kt

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Edit : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)

        sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)

        val nameEditText: EditText = findViewById(R.id.NameEditText)
        val numberEditText: EditText = findViewById(R.id.NumberEditText)
        val saveButton: Button = findViewById(R.id.saveButton)

        // Load existing preferences
        nameEditText.setText(sharedPreferences.getString("preferredName", ""))
        numberEditText.setText(sharedPreferences.getString("editableNumber", ""))

        saveButton.setOnClickListener {
            // Save the updated preferences
            val preferredName = nameEditText.text.toString()
            val editableNumber = numberEditText.text.toString()

            saveUserPreferences(preferredName, editableNumber)



            // Notify the Profile activity about the change
            val data = Intent()
            data.putExtra("preferredName", preferredName)
            data.putExtra("editableNumber", editableNumber)
            setResult(RESULT_OK, data)
            showToast("Profile Saved successfully")
            // Navigate back to the Dashboard activity
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)

            // Finish the Edit activity
            finish()
        }

        // Back button functionality
        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun saveUserPreferences(preferredName: String, editableNumber: String) {
        val editor = sharedPreferences.edit()
        editor.putString("preferredName", preferredName)
        editor.putString("editableNumber", editableNumber)
        editor.apply()

    }
    private fun showToast(message: String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }
}