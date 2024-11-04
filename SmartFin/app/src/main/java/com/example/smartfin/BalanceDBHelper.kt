package com.example.smartfin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.room.util.getColumnIndex

class BalanceDBHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "budgets.dc"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "budget"
        private const val COLUMN_ID = "id"
        private const val COLUMN_WEEKLY_BUDGET = "weekly_budget"
        private const val COLUMN_MONTHLY_BUDGET = "monthly_budget"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_WEEKLY_BUDGET REAL, " +
                "$COLUMN_MONTHLY_BUDGET REAL)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addBudget(weeklyBudget: Double, monthlyBudget: Double): Long {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_WEEKLY_BUDGET, weeklyBudget)
            put(COLUMN_MONTHLY_BUDGET, monthlyBudget)
        }
        // Try updating first
        val updatedRows = db.update(TABLE_NAME, contentValues, null, null)
        if (updatedRows == 0) { // If no rows were updated, then insert a new row
            val result = db.insert(TABLE_NAME, null, contentValues)
            db.close()
            return result
        } else {
            db.close()
            return updatedRows.toLong()
        }
    }

    @SuppressLint("Range")
    fun getweeklyBudget(): Double {
        val db = readableDatabase
        val query = "SELECT $COLUMN_WEEKLY_BUDGET FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        var weeklyBudget = 0.0
        if (cursor.moveToFirst()) {
            weeklyBudget = cursor.getDouble(cursor.getColumnIndex(COLUMN_WEEKLY_BUDGET))

        }
        cursor.close()
   return weeklyBudget
    }
    @SuppressLint("Range")
    fun getMonthlyBudget(): Double {
        val db = readableDatabase
        val query = "SELECT $COLUMN_MONTHLY_BUDGET FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        var monthlyBudget = 0.0
        if (cursor.moveToFirst()) {
            monthlyBudget = cursor.getDouble(cursor.getColumnIndex(COLUMN_MONTHLY_BUDGET))
        }
        cursor.close()
        return monthlyBudget

    }
    fun resetBudgets(){
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_WEEKLY_BUDGET, 0.0) // Reset weekly budget to 0
            put(COLUMN_MONTHLY_BUDGET, 0.0) // Reset monthly budget to 0
        }
        db.update(TABLE_NAME, contentValues, null, null)
        db.close()

    }
}
