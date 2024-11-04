package com.example.pama

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val profile: CardView = findViewById(R.id.profile)
        val missedCalls: CardView = findViewById(R.id.MissedCalls)
        val dial: CardView = findViewById(R.id.Dial)
        val mail: CardView = findViewById(R.id.mail)
        val sms: CardView = findViewById(R.id.SMS)
        val music: CardView = findViewById(R.id.music)
        val search: CardView = findViewById(R.id.Search)
        val help: CardView = findViewById(R.id.help)
        val settings: CardView = findViewById(R.id.settings)
        val username = intent.getStringExtra("username")

        val phoneNumber = intent.getStringExtra("phoneNumber")


        val welcomeText = findViewById<TextView>(R.id.top_view)
        welcomeText.text = "Welcome $username"


        profile.setOnClickListener{
            val profileIntent = Intent(this@Dashboard,Profile::class.java)
            profileIntent.putExtra("name", username)
            profileIntent.putExtra("phoneNumber", phoneNumber)
            startActivity(profileIntent)

        }
        missedCalls.setOnClickListener{
            val missedIntent = Intent(this@Dashboard, MissedCalls::class.java)
            startActivity(missedIntent)
        }
        dial.setOnClickListener{
            val dialIntent = Intent(this@Dashboard, Dialer::class.java)
            startActivity(dialIntent)
        }
        mail.setOnClickListener{
            val mailIntent = Intent(this@Dashboard, EmailActivity::class.java)
            startActivity(mailIntent)
        }
        sms.setOnClickListener{
            val smsIntent = Intent(this@Dashboard, SMSActivity::class.java)
            startActivity(smsIntent)
        }
        search.setOnClickListener{
            val searchIntent = Intent(this@Dashboard, SearchActivity::class.java)
            startActivity(searchIntent)
        }
        music.setOnClickListener{
            val musicIntent = Intent(this@Dashboard, PlayMusicActivity::class.java)
            startActivity(musicIntent)
        }
        help.setOnClickListener{
            val helpIntent = Intent(this@Dashboard, HelpActivity::class.java)
            startActivity(helpIntent)
        }
        settings.setOnClickListener {
            val settingsIntent = Intent(this@Dashboard, SettingsActivity::class.java)
          startActivity(settingsIntent)
        }


        val editButton: ImageView = findViewById(R.id.editButton)
        editButton.setOnClickListener {
            clearUserSession()
            val intent = Intent(this, Login:: class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }

    }
    private fun clearUserSession() {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn",false)
        editor.apply()
    }
}