package com.example.pama

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SMSActivity : AppCompatActivity() {

    private lateinit var editTextPhoneNumber: EditText
    private lateinit var editTextMessage: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)

        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber)
        editTextMessage = findViewById(R.id.editTextMessage)

        val buttonSendSMS: Button = findViewById(R.id.buttonSendSMS)
        buttonSendSMS.setOnClickListener {
            sendSMS()
        }

        // Request SMS permission if not granted
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.SEND_SMS),
                123
            )
        }
    }

    fun sendSMS() {
        val phoneNumber = editTextPhoneNumber.text.toString().trim()
        val message = editTextMessage.text.toString().trim()

        if (phoneNumber.isNotEmpty() && message.isNotEmpty()) {
            val smsUri = Uri.parse("smsto:$phoneNumber")
            val smsIntent = Intent(Intent.ACTION_SENDTO, smsUri)
            smsIntent.putExtra("sms_body", message)

            if (smsIntent.resolveActivity(packageManager) != null) {
                startActivity(smsIntent)
                // Show a "SMS sent successfully" toast
                showToast("SMS sent successfully")
            } else {
                // Show a "No SMS app found" toast
                showToast("No SMS app found on your device")
            }
        } else {
            // Show a "Please enter a phone number and message" toast
            showToast("Please enter a phone number and message")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }
}
