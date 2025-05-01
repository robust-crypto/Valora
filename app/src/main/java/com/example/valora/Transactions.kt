package com.example.valora

import java.io.Serializable

data class Transaction(
    val title: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val amount: Double,
    val imageUri: String?
) : Serializable

