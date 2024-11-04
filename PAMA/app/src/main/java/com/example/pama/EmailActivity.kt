package com.example.pama

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EmailActivity : AppCompatActivity() {

    private lateinit var editTextEmailAddress: EditText
    private lateinit var editTextSubject: EditText
    private lateinit var editTextMessage: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)

        editTextEmailAddress = findViewById(R.id.editTextEmailAddress)
        editTextSubject = findViewById(R.id.editTextSubject)
        editTextMessage = findViewById(R.id.editTextMessage)

        val buttonSendEmail: Button = findViewById(R.id.buttonSendEmail)
        buttonSendEmail.setOnClickListener {
            sendEmail()
        }
    }

    private fun sendEmail() {
        val emailAddress = editTextEmailAddress.text.toString().trim()
        val subject = editTextSubject.text.toString().trim()
        val message = editTextMessage.text.toString().trim()

        if (emailAddress.isNotEmpty() && subject.isNotEmpty() && message.isNotEmpty()) {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "message/rfc822"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, message)

            if (emailIntent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(emailIntent, "Choose an Email client:"))
                // Show a "Email sent successfully" toast
                showToast("Email sent successfully")
            } else {
                // Handle the case where no email app is available
                // You might want to display a message to the user
                showToast("No email app found on your device")
            }
        } else {
            // Show a "Please fill in all fields" toast
            showToast("Please fill in all fields")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

