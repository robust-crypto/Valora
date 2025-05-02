package com.example.budgetapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.valora.R
import java.util.*

class AddBudgetActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var textViewDate: TextView
    private lateinit var textViewStartTime: TextView
    private lateinit var textViewEndTime: TextView
    private lateinit var editTextMinAmount: EditText
    private lateinit var editTextMaxAmount: EditText
    private lateinit var editTextAmount: EditText
    private lateinit var buttonSubmit: Button

    private var selectedDate: String? = null
    private var selectedStartTime: String? = null
    private var selectedEndTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.budget_bottom_sheet_input)

        // Initialize views
        editTextTitle = findViewById(R.id.editTextTitle)
        textViewDate = findViewById(R.id.textViewBudgetDate)
        textViewStartTime = findViewById(R.id.textViewBudgetStartTime)
        textViewEndTime = findViewById(R.id.textViewBudgetEndTime)
        editTextMinAmount = findViewById(R.id.editTextMinAmount)
        editTextMaxAmount = findViewById(R.id.editTextMaxAmount)
        editTextAmount = findViewById(R.id.editTextAmount)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        // Date picker
        textViewDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    selectedDate = "$dayOfMonth/${month + 1}/$year"
                    textViewDate.text = "Selected Date: $selectedDate"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Start time picker
        textViewStartTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            TimePickerDialog(
                this,
                { _, hour, minute ->
                    selectedStartTime = String.format("%02d:%02d", hour, minute)
                    textViewStartTime.text = "Start Time: $selectedStartTime"
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        // End time picker
        textViewEndTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            TimePickerDialog(
                this,
                { _, hour, minute ->
                    selectedEndTime = String.format("%02d:%02d", hour, minute)
                    textViewEndTime.text = "End Time: $selectedEndTime"
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        // Submit button
        buttonSubmit.setOnClickListener {
            val title = editTextTitle.text.toString().trim()
            val minAmount = editTextMinAmount.text.toString().toDoubleOrNull()
            val maxAmount = editTextMaxAmount.text.toString().toDoubleOrNull()
            val amount = editTextAmount.text.toString().toDoubleOrNull()

            if (TextUtils.isEmpty(title) || selectedDate == null || selectedStartTime == null ||
                selectedEndTime == null || minAmount == null || maxAmount == null || amount == null) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultIntent = intent.apply {
                putExtra("title", title)
                putExtra("date", selectedDate)
                putExtra("startTime", selectedStartTime)
                putExtra("endTime", selectedEndTime)
                putExtra("minAmount", minAmount)
                putExtra("maxAmount", maxAmount)
                putExtra("amount", amount)
            }

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
