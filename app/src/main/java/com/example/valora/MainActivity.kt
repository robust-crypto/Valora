package com.example.valora

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var emailInput :EditText
    lateinit var passwordInput : EditText
    lateinit var loginBtn : Button
    lateinit var signUpLink : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        loginBtn = findViewById(R.id.login_btn)
        signUpLink = findViewById(R.id.signup_link)

       loginBtn.setOnClickListener {
           val email = emailInput.text.toString()
           val password = passwordInput.text.toString()

           Toast.makeText(this, "User with email: $email & password:$password has logged in.", Toast.LENGTH_SHORT).show()


       }
        signUpLink.setOnClickListener {
            val intent = Intent(this,Registration::class.java)
            startActivity(intent)
        }


        }
    }
