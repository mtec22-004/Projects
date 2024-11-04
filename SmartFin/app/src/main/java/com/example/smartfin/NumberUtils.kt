package com.example.smartfin

import java.text.DecimalFormat

object NumberUtils {
    fun formatNumber(amount: Double): String {
        val formatter = DecimalFormat("#,###.00")
        return "P" + formatter.format(amount)
    }
}