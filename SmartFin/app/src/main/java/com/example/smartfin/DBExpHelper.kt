package com.example.smartfin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBExpHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "expense.db"
        private const val TABLE_NAME = "expense"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_AMOUNT = "amount"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME " +
        "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "$COLUMN_NAME TEXT, " +
        "$COLUMN_AMOUNT REAL)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun insertExpenses(expense : dataExpenses) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, expense.name)
            put(COLUMN_AMOUNT, expense.amount)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun updateExpenses(expense: dataExpenses){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, expense.name)
            put(COLUMN_AMOUNT, expense.amount)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(expense.id.toString()))
        db.close()
    }
    fun deleteExpense(expenseId: Long){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(expenseId.toString()))
        db.close()
    }
    @SuppressLint("Range")
    fun getAllExpenses(): List<dataExpenses> {
        val expenseList = mutableListOf<dataExpenses>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT))
                expenseList.add(dataExpenses(id, name, amount))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return expenseList
    }

}
