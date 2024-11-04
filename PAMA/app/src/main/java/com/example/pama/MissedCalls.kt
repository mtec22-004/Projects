package com.example.pama

// MissedCallsActivity.kt
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.CallLog
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MissedCalls : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var textViewEmpty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_missedcall)

        recyclerView = findViewById(R.id.recyclerViewMissedCalls)
        textViewEmpty = findViewById(R.id.textViewEmpty)

        if (hasCallLogPermission()) {
            val missedCallsList = fetchMissedCalls()
            if (missedCallsList.isNotEmpty()) {
                textViewEmpty.visibility = View.GONE
                setupRecyclerView(missedCallsList)
            } else {
                textViewEmpty.visibility = View.VISIBLE
            }
        } else {
            requestCallLogPermission()
        }
    }

    private fun hasCallLogPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CALL_LOG
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun requestCallLogPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CALL_LOG),
                REQUEST_CALL_LOG_PERMISSION
            )
        }
    }

    private fun fetchMissedCalls(): List<MissedCall> {
        val missedCallsList = mutableListOf<MissedCall>()
        val projection = arrayOf(
            CallLog.Calls.CACHED_NAME,
            CallLog.Calls.NUMBER,
            CallLog.Calls.DATE
        )

        val cursor = contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            projection,
            CallLog.Calls.TYPE + " = ?",
            arrayOf(CallLog.Calls.MISSED_TYPE.toString()),
            CallLog.Calls.DATE + " DESC"
        )

        cursor?.use {
            val nameColumn = it.getColumnIndex(CallLog.Calls.CACHED_NAME)
            val numberColumn = it.getColumnIndex(CallLog.Calls.NUMBER)
            val dateColumn = it.getColumnIndex(CallLog.Calls.DATE)

            while (it.moveToNext()) {
                val callerName = it.getString(nameColumn)
                val callerNumber = it.getString(numberColumn)
                val callTime = it.getString(dateColumn)?.let { timestamp ->
                    val callDateTime = Date(timestamp.toLong())
                    SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(callDateTime)
                } ?: ""

                val displayText = if (callerName.isNullOrEmpty()) callerNumber ?: "Unknown" else callerName
                missedCallsList.add(MissedCall(displayText, callTime))
            }
        }

        return missedCallsList
    }

    private fun setupRecyclerView(missedCallsList: List<MissedCall>) {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val adapter = MissedCallsAdapter(missedCallsList)
        recyclerView.adapter = adapter
    }

    companion object {
        private const val REQUEST_CALL_LOG_PERMISSION = 1
    }
}
