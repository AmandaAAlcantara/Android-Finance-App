package com.example.mob_dev_portfolio.nav_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.mob_dev_portfolio.budget_transaction.AppDatabase
import com.example.mob_dev_portfolio.budget_transaction.FoodTransactionAdapter
import com.example.mob_dev_portfolio.budget_transaction.Transaction
import com.example.mob_dev_portfolio.budget_transaction.TransactionAdapter
import com.example.mob_dev_portfolio.databinding.FragmentFoodBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FoodFragment : Fragment() {

    private var _binding: FragmentFoodBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactions: List<Transaction>
    private lateinit var foodTransactions: List<Transaction>
    private lateinit var foodTransactionAdapter: FoodTransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        foodTransactions = ArrayList()
        foodTransactionAdapter = FoodTransactionAdapter(foodTransactions)
        linearLayoutManager = LinearLayoutManager(context)

        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "transactions").build()



        // Bind RecyclerView
        binding.foodRecyclerview.apply {
            adapter = foodTransactionAdapter
            layoutManager = linearLayoutManager
        }
        // Fetch food transactions
    }


    private fun fetchFoodTransactions() {
        GlobalScope.launch {
            transactions = db.transactionDao().getAll()
            val foodTransactions = db.transactionDao().getAllTypeFood()
            requireActivity().runOnUiThread {
                totalAmountOfTransactions()
                foodTransactionAdapter.setData(foodTransactions)
            }
        }
    }

    private fun totalAmountOfTransactions() {
        val budgetAmount = transactions.filter { it.amount > 0 }.map { it.amount }.sum()
        val totalFoodAmount = Math.abs(transactions.filter { it.type == "Food" }.map { it.amount }.sum())
        val foodPercentage = if (budgetAmount != 0.0) {
            (totalFoodAmount / budgetAmount) * 100
        } else {
            0.0
        }
        binding.percentageFood.text = "%.2f".format(foodPercentage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        fetchFoodTransactions()
    }
}
