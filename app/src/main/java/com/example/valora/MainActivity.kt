package com.example.valora

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    lateinit var emailInput: EditText
    lateinit var passwordInput: EditText
    lateinit var loginBtn: Button
    lateinit var signUpLink: TextView

    // Define permission request codes
    private val REQUEST_CODE_PERMISSION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize views
        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        loginBtn = findViewById(R.id.login_btn)
        signUpLink = findViewById(R.id.signup_link)

        // Check and request permissions
        checkPermissions()

        // Login button click listener
        loginBtn.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            Toast.makeText(this, "User with email: $email & password: $password has logged in.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // Sign up link click listener
        signUpLink.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
    }

    // Function to check if permissions are granted
    private fun checkPermissions() {
        // Check if storage permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // Request permissions if not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                ),
                REQUEST_CODE_PERMISSION
            )
        } else {
            // Permissions are granted, proceed with normal app functionality
            proceedWithAppFunctionality()
        }
    }

    // Handle the result of permission requests
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // Permissions granted, proceed with the functionality
                proceedWithAppFunctionality()
            } else {
                // Permissions denied, show a message
                Toast.makeText(this, "Permission denied to access required features.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // This method contains the logic you want to execute after the permission is granted
    private fun proceedWithAppFunctionality() {
        // Example: Proceed with the login or other app functionality
        Toast.makeText(this, "Permissions granted! Proceeding with app functionality.", Toast.LENGTH_SHORT).show()
    }
}
