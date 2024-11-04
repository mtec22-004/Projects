package com.example.smartfin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IncomeDao {
    @Query("SELECT * FROM income_table")
    fun getAllIncomes(): List<data_Income>

    @Insert
    suspend fun insertIncome(income: Income)
}
