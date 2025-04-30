package com.example.valora

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.example.valora.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(fragment_home())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->

            when(item.itemId){
                R.id.home -> replaceFragment(fragment_home())
                R.id.transactions -> replaceFragment(fragment_transactions())
                R.id.budgets -> replaceFragment(fragment_budgets())
                R.id.settings -> replaceFragment(fragment_settings())

                else ->{

                }
            }
            true
        }

        }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }


    }
