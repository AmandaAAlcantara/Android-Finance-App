package com.example.mob_dev_portfolio.nav_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mob_dev_portfolio.budget_transaction.AppDatabase
import com.example.mob_dev_portfolio.budget_transaction.SavingsTransactionAdapter
import com.example.mob_dev_portfolio.budget_transaction.Transaction
import com.example.mob_dev_portfolio.databinding.FragmentSavingsBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SavingsFragment : Fragment() {

    private var _binding: FragmentSavingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactions: List<Transaction>
    private lateinit var savingsTransactions: List<Transaction>
    private lateinit var savingsTransactionAdapter: SavingsTransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        savingsTransactions = ArrayList()
        savingsTransactionAdapter = SavingsTransactionAdapter(savingsTransactions)
        linearLayoutManager = LinearLayoutManager(context)

        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "transactions").build()


        // Bind RecyclerView
        binding.savingsRecyclerview.apply {
            adapter = savingsTransactionAdapter
            layoutManager = linearLayoutManager
        }
        // Fetch food transactions
    }


    private fun fetchSavingsTransactions() {
        GlobalScope.launch {
            transactions = db.transactionDao().getAll()
            val savingsTransactions = db.transactionDao().getAllTypeSavings()
            requireActivity().runOnUiThread {
                totalAmountOfTransactions()
                savingsTransactionAdapter.setData(savingsTransactions)
            }
        }
    }

    private fun totalAmountOfTransactions() {
        val budgetAmount = transactions.filter { it.amount > 0 }.map { it.amount }.sum()
        val totalSavingsAmount = Math.abs(transactions.filter { it.type == "Savings" }.map { it.amount }.sum())
        val savingsPercentage = if (budgetAmount != 0.0) {
            (totalSavingsAmount / budgetAmount) * 100
        } else {
            0.0
        }
        binding.percentageSavings.text = "%.2f".format(savingsPercentage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        fetchSavingsTransactions()
    }
}
