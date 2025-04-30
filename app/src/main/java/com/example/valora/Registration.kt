package com.example.valora

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Registration : AppCompatActivity() {

    lateinit var emailInput : EditText
    lateinit var passwordInput : EditText
    lateinit var confirmPasswordInput: EditText
    lateinit var firstNameInput : EditText
    lateinit var surnameInput : EditText
    lateinit var currencySpinner : Spinner
    lateinit var loginLink : TextView
    lateinit var registerBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)

        emailInput = findViewById(R.id.email_input)
        firstNameInput = findViewById(R.id.firstName_input)
        surnameInput = findViewById(R.id.surname_input)
        passwordInput = findViewById(R.id.password_input)
        confirmPasswordInput = findViewById(R.id.confirmPassword_input)
        currencySpinner = findViewById(R.id.mySpinner)
        registerBtn = findViewById(R.id.register_btn)
        loginLink = findViewById(R.id.login_link)

        registerBtn.setOnClickListener {
            val email = emailInput.text.toString()
            val firstName = firstNameInput.text.toString()
            val surname = surnameInput.text.toString()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            Toast.makeText(this, "User with email: $email, $firstName, $surname & password:$password has registered.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,Home::class.java)
            startActivity(intent)
        }

        loginLink.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }



        }
    }
