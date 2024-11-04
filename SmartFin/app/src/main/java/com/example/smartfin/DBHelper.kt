package com.example.smartfin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "income.db"
        private const val TABLE_NAME = "income"
        const val COLUMN_ID = "id"
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

    fun insertIncome(income: data_Income) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, income.name)
            put(COLUMN_AMOUNT, income.amount)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateIncome(income: data_Income) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, income.name)
            put(COLUMN_AMOUNT, income.amount)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(income.id.toString()))
        db.close()
    }

    fun deleteIncome(incomeId: Long) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(incomeId.toString()))
        db.close()
    }

    @SuppressLint("Range")
    fun getAllIncomes(): List<data_Income> {
        val incomeList = mutableListOf<data_Income>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT))
                incomeList.add(data_Income(id, name, amount))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return incomeList
    }

    // Implement other CRUD operations as needed
}
