package com.example.valora

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TransactionDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions_detail)

        val transaction: Transaction? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("transaction", Transaction::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("transaction") as? Transaction
        }

        // Check for null transaction
        if (transaction == null) {
            Toast.makeText(this, "Transaction not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Set values or show placeholder text
        findViewById<TextView>(R.id.textViewTitle).text = transaction?.title ?: "N/A"
        findViewById<TextView>(R.id.textViewDate).text = transaction?.date ?: "N/A"
        findViewById<TextView>(R.id.textViewStartTime).text = transaction?.startTime ?: "N/A"
        findViewById<TextView>(R.id.textViewEndTime).text = transaction?.endTime ?: "N/A"
        findViewById<TextView>(R.id.textViewAmount).text = "R${transaction?.amount ?: "0.00"}"

        // Load image using URI
        val imageView = findViewById<ImageView>(R.id.imageView)
        transaction?.imageUri?.let {
            imageView.setImageURI(Uri.parse(it))
        }
    }
}
