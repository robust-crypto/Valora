package com.example.valora

import BudgetAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetapp.AddBudgetActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class fragment_budgets : Fragment() {

    private lateinit var budgetsRecyclerView: RecyclerView
    private lateinit var budgetsAdapter: BudgetAdapter
    private val budgetsList = mutableListOf<Budget>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflater.inflate(R.layout.fragment_budgets, container, false)

        budgetsRecyclerView = binding.findViewById(R.id.recyclerViewBudgets)
        budgetsAdapter = BudgetAdapter(budgetsList)
        budgetsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        budgetsRecyclerView.adapter = budgetsAdapter

        val buttonAddBudget = binding.findViewById<FloatingActionButton>(R.id.fabAddBudget)
        buttonAddBudget.setOnClickListener {
            // Use the fragment's activity to start the AddBudgetActivity
            val intent = Intent(requireContext(), AddBudgetActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_BUDGET)
        }

        return binding
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_ADD_BUDGET && resultCode == Activity.RESULT_OK && data != null) {
            val title = data.getStringExtra("title") ?: return
            val date = data.getStringExtra("date") ?: return
            val startTime = data.getStringExtra("startTime") ?: return
            val endTime = data.getStringExtra("endTime") ?: return
            val minAmount = data.getDoubleExtra("minAmount", 0.0)
            val maxAmount = data.getDoubleExtra("maxAmount", 0.0)
            val amount = data.getDoubleExtra("amount", 0.0)

            val newBudget = Budget(title, date, startTime, endTime, minAmount, maxAmount, amount)
            budgetsList.add(newBudget)
            budgetsAdapter.notifyItemInserted(budgetsList.size - 1)
        }
    }

    companion object {
        const val REQUEST_CODE_ADD_BUDGET = 1
    }
}
