package com.example.pama

import android.Manifest
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

class Dialer : AppCompatActivity() {

    private lateinit var editTextPhoneNumber: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialer)

        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber)
        val buttonDial: Button = findViewById(R.id.buttonDial)

        buttonDial.setOnClickListener {
            dialNumber()
        }
    }

    private fun dialNumber() {
        val phoneNumber = editTextPhoneNumber.text.toString().trim()

        if (phoneNumber.isNotEmpty()) {
            // Check for the CALL_PHONE permission before initiating the call
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val dialIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
                startActivity(dialIntent)
            } else {
                // Request the CALL_PHONE permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    REQUEST_CALL_PHONE_PERMISSION
                )
            }
        } else {
            showToast("Please enter a phone number")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUEST_CALL_PHONE_PERMISSION = 1
    }
}
