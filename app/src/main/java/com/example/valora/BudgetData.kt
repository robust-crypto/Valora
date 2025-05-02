package com.example.valora

data class Budget(
    val title: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val minAmount: Double,
    val maxAmount: Double,
    val amount: Double
)
