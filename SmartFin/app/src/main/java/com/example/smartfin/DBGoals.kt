package com.example.smartfin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBGoals(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "goals.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "goals"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_PROGRESS = "progress"
        private const val COLUMN_TARGET_AMOUNT = "target_amount"
        private const val COLUMN_CURRENT_AMOUNT = "current_amount"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITLE TEXT, " +
                "$COLUMN_PROGRESS INTEGER, " +
                "$COLUMN_TARGET_AMOUNT REAL, " +
                "$COLUMN_CURRENT_AMOUNT REAL)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getAllGoals(): List<data_Goal> {
        val goalsList = mutableListOf<data_Goal>()
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)

        cursor.use {
            while (it.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                val title = it.getString(it.getColumnIndex(COLUMN_TITLE))
                val progress = it.getInt(it.getColumnIndex(COLUMN_PROGRESS))
                val targetAmount = it.getDouble(it.getColumnIndex(COLUMN_TARGET_AMOUNT))
                val currentAmount = it.getDouble(it.getColumnIndex(COLUMN_CURRENT_AMOUNT))

                val goal = data_Goal(id, title, progress, targetAmount, currentAmount)
                goalsList.add(goal)
            }
        }

        return goalsList
    }
    fun updateGoal(updatedGoal: data_Goal) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CURRENT_AMOUNT, updatedGoal.currentAmount)
            put(COLUMN_PROGRESS, (updatedGoal.currentAmount / updatedGoal.targetAmount * 100).toInt())
        }
        db.update(
            TABLE_NAME,
            values,
            "$COLUMN_ID=?",
            arrayOf(updatedGoal.id.toString())
        )
        db.close()
    }


    fun insertGoal(newGoal: data_Goal) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, newGoal.title)
            put(COLUMN_PROGRESS, newGoal.progress)
            put(COLUMN_TARGET_AMOUNT, newGoal.targetAmount)
            put(COLUMN_CURRENT_AMOUNT, newGoal.currentAmount)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun deleteGoals(goalId: Long): Int {
        val db = writableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(goalId.toString()))
        db.close()
        return result
    }

}
