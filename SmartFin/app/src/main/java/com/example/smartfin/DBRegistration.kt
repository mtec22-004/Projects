package com.example.smartfin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBRegistration(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "users"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME " +
                "($COLUMN_USERNAME TEXT PRIMARY KEY, " +
                "$COLUMN_EMAIL TEXT, " +
                "$COLUMN_PASSWORD TEXT)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addUser(username: String, email: String, password: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
        }

        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    @SuppressLint("Range")
    fun getLoggedInUsername(): String? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME LIMIT 1", null)
        var username: String? = null

        if (cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
        }

        cursor.close()
        db.close()

        return username
    }

    fun signOutUser(): Boolean {
        val db = this.writableDatabase
        // Assuming signing out just involves deleting the current user.
        // Note: This is a simplistic approach; usually, you'd handle session tokens or similar.
        val deleteUser = db.delete(TABLE_NAME, null, null) > 0
        db.close()
        return deleteUser
    }

    fun updateUsername(newUsername: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, newUsername)
        }

        // Update the username in the database
        val updatedRows = db.update(TABLE_NAME, values, null, null)
        db.close()

        // Return true if at least one row was updated
        return updatedRows > 0
    }
}
