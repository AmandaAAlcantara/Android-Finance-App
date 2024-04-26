package com.example.mob_dev_portfolio.nav_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mob_dev_portfolio.budget_transaction.AppDatabase
import com.example.mob_dev_portfolio.budget_transaction.Transaction
import com.example.mob_dev_portfolio.budget_transaction.UtilitiesTransactionAdapter
import com.example.mob_dev_portfolio.databinding.FragmentUtilitiesBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UtilitiesFragment : Fragment() {

    private var _binding: FragmentUtilitiesBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactions: List<Transaction>
    private lateinit var utilitiesTransactions: List<Transaction>
    private lateinit var utilitiesTransactionAdapter: UtilitiesTransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUtilitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        utilitiesTransactions = ArrayList()
        utilitiesTransactionAdapter = UtilitiesTransactionAdapter(utilitiesTransactions)
        linearLayoutManager = LinearLayoutManager(context)

        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "transactions").build()



        // Bind RecyclerView
        binding.foodRecyclerview.apply {
            adapter = utilitiesTransactionAdapter
            layoutManager = linearLayoutManager
        }
        // Fetch food transactions
    }


    private fun fetchUtilitiesTransactions() {
        GlobalScope.launch {
            transactions = db.transactionDao().getAll()
            val utilitiesTransactions = db.transactionDao().getAllTypeUtilities()
            requireActivity().runOnUiThread {
                totalAmountOfTransactions()
                utilitiesTransactionAdapter.setData(utilitiesTransactions)
            }
        }
    }

    private fun totalAmountOfTransactions() {
        val budgetAmount = transactions.filter { it.amount > 0 }.map { it.amount }.sum()
        val totalUtilitiesAmount = Math.abs(transactions.filter { it.type == "Utilities" }.map { it.amount }.sum())
        val foodPercentage = if (budgetAmount != 0.0) {
            (totalUtilitiesAmount / budgetAmount) * 100
        } else {
            0.0
        }
        binding.percentageUtilities.text = "%.2f".format(foodPercentage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        fetchUtilitiesTransactions()
    }
}


