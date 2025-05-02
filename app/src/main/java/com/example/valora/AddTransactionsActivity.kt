package com.example.valora

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.util.Calendar

class AddTransactionsActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var amountEditText: EditText
    private lateinit var datePicker: DatePicker
    private lateinit var startTimePicker: TimePicker
    private lateinit var endTimePicker: TimePicker
    private lateinit var imageView: ImageView
    private lateinit var imageUri: Uri

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
            imageView.setImageURI(it)
        }
    }

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val uri = saveImageToCache(it)
            imageUri = uri
            imageView.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transactions_bottom_sheet_input)

        titleEditText = findViewById(R.id.editTextTitle)
        amountEditText = findViewById(R.id.editTextAmount)
        val dateText = findViewById<TextView>(R.id.textViewDate)
        val startTimeText = findViewById<TextView>(R.id.textViewStartTime)
        val endTimeText = findViewById<TextView>(R.id.textViewEndTime)

        setupDateTimePickers(dateText, startTimeText, endTimeText)

        imageView = findViewById(R.id.imageView)

        val buttonUploadImage = findViewById<Button>(R.id.buttonUploadImage)
        buttonUploadImage.setOnClickListener {
            val options = arrayOf("Choose from Gallery", "Take a Photo")
            AlertDialog.Builder(this)
                .setTitle("Select Image Source")
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> imagePickerLauncher.launch("image/*")   // Gallery
                        1 -> cameraLauncher.launch(null)             // Camera
                    }
                }
                .show()
        }

        val buttonSave = findViewById<Button>(R.id.buttonSubmit)
        buttonSave.setOnClickListener {
            saveTransaction()
        }
    }
    private fun setupDateTimePickers(
        dateTextView: TextView,
        startTimeTextView: TextView,
        endTimeTextView: TextView
    ) {
        val calendar = Calendar.getInstance()

        dateTextView.setOnClickListener {
            DatePickerDialog(
                dateTextView.context,
                { _, year, month, day ->
                    dateTextView.text = "$day/${month + 1}/$year"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val timeSetListener = { view: TextView ->
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(
                view.context,
                { _, hour, minute ->
                    view.text = String.format("%02d:%02d", hour, minute)
                },
                currentHour,
                currentMinute,
                true
            ).show()
        }

        startTimeTextView.setOnClickListener {
            timeSetListener(startTimeTextView)
        }

        endTimeTextView.setOnClickListener {
            timeSetListener(endTimeTextView)
        }
    }


    private fun saveTransaction() {
        val title = titleEditText.text.toString()
        val amount = amountEditText.text.toString().toDoubleOrNull()

        val dateTextView = findViewById<TextView>(R.id.textViewDate)
        val startTimeTextView = findViewById<TextView>(R.id.textViewStartTime)
        val endTimeTextView = findViewById<TextView>(R.id.textViewEndTime)

        val date = dateTextView.text.toString()
        val startTime = startTimeTextView.text.toString()
        val endTime = endTimeTextView.text.toString()

        if (title.isEmpty() || amount == null || date.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
            return
        }

        val transaction = Transaction(
            title = title,
            date = date,
            startTime = startTime,
            endTime = endTime,
            amount = amount,
            imageUri = if (::imageUri.isInitialized) imageUri.toString() else null
        )

        val intent = Intent().apply {
            putExtra("transaction", transaction)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun saveImageToCache(bitmap: Bitmap): Uri {
        val filename = "image_${System.currentTimeMillis()}.jpg"
        val file = File(cacheDir, filename)
        file.outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        return FileProvider.getUriForFile(this, "${packageName}.provider", file)
    }
}
