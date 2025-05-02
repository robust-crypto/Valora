package com.example.valora

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TransactionsAdapter(
    private val transactions: List<Transaction>,
    private val onItemClick: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.transactions_name)
        val category: TextView = itemView.findViewById(R.id.category_name)
        val amount: TextView = itemView.findViewById(R.id.description)
        val image: ImageView = itemView.findViewById(R.id.selectedImage)

        fun bind(transaction: Transaction) {
            title.text = transaction.title
            category.text = transaction.date // You can change this to actual category if needed
            amount.text = "R${transaction.amount}"

            if (!transaction.imageUri.isNullOrEmpty()) {
                image.setImageURI(Uri.parse(transaction.imageUri))
            } else {
                image.setImageResource(R.drawable.image_24dp_e3e3e3_fill0_wght400_grad0_opsz24)
            }

            itemView.setOnClickListener {
                // Log click event to see if it's firing
                Log.d("TransactionAdapter", "Item clicked: ${transaction.title}")
                Toast.makeText(
                    itemView.context,
                    "Clicked: ${transaction.title}",
                    Toast.LENGTH_SHORT
                ).show()
                onItemClick(transaction)

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transactions_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = transactions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactions[position])
    }
}
