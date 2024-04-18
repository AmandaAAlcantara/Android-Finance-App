package com.example.mob_dev_portfolio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mob_dev_portfolio.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var transactions: ArrayList<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: ActivityMain2Binding // Data binding instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater) // Inflate using data binding
        setContentView(binding.root)

        // Initialize RecyclerView
        transactions = arrayListOf(
            Transaction("Weekend budget", 400.00),
            Transaction("Coffee", -5.00),
            Transaction("Lunch", -10.00),
            Transaction("Petrol", -40.00),
            Transaction("Dinner", -10.00),
            Transaction("Drink", -3.00),
            Transaction("Car Park", -1.00),
            Transaction("Night Out", -33.00),
        )
        transactionAdapter = TransactionAdapter(transactions)
        linearLayoutManager = LinearLayoutManager(this)

        // Bind RecyclerView using data binding
        binding.recyclerview.apply {
            adapter = transactionAdapter
            layoutManager = linearLayoutManager
        }

        updateDashboard()
    }

    private fun updateDashboard(){
        val totalAmount = transactions.map { it.amount }.sum()
        val budgetAmount = transactions.filter { it.amount>0 }.map{it.amount}.sum()
        val expenseAmount = totalAmount -  budgetAmount

        binding.balance.text = "$ %.2f".format(totalAmount)
        binding.budget.text = "$ %.2f".format(budgetAmount)
        binding.expense.text = "$ %.2f".format(expenseAmount)
    }
}