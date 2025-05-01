package com.example.valora

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.valora.databinding.FragmentTransactionsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class fragment_transactions : Fragment() {

    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactionList: MutableList<Transaction>
    private lateinit var adapter: TransactionsAdapter

    private val addTransactionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val transaction = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("transaction", Transaction::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("transaction") as? Transaction
            }
            transaction?.let { tx ->
                transactionList.add(tx)
                adapter.notifyItemInserted(transactionList.size - 1)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)

        // Initialize the transaction list and adapter
        transactionList = mutableListOf()
        adapter = TransactionsAdapter(transactionList) { transaction ->
            // On item click, open the details of the transaction
            val intent = Intent(requireContext(), TransactionDetailActivity::class.java)
            intent.putExtra("transaction", transaction)  // Send the transaction object to the detail activity
            startActivity(intent)
        }

        binding.recyclerViewTransactions.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTransactions.adapter = adapter

        // Set up the FloatingActionButton to launch the AddTransactionsActivity
        binding.fabAddTransactions.setOnClickListener {
            val intent = Intent(requireContext(), AddTransactionsActivity::class.java)
            addTransactionLauncher.launch(intent)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
