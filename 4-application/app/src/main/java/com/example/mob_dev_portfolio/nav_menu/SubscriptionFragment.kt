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
import com.example.mob_dev_portfolio.databinding.FragmentSubscriptionBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SubscriptionFragment : Fragment() {

    private var _binding: FragmentSubscriptionBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactions: List<Transaction>
    private lateinit var subscriptionTransactions: List<Transaction>
    private lateinit var subscriptionTransactionAdapter: SavingsTransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubscriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        subscriptionTransactions = ArrayList()
        subscriptionTransactionAdapter = SavingsTransactionAdapter(subscriptionTransactions)
        linearLayoutManager = LinearLayoutManager(context)

        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "transactions").build()


        // Bind RecyclerView
        binding.subscriptionRecyclerview.apply {
            adapter = subscriptionTransactionAdapter
            layoutManager = linearLayoutManager
        }
        // Fetch food transactions
    }


    private fun fetchSubsctiptionTransactions() {
        GlobalScope.launch {
            transactions = db.transactionDao().getAll()
            val subscriptionTransactions = db.transactionDao().getAllTypeSubscriptions()
            requireActivity().runOnUiThread {
                totalAmountOfTransactions()
                subscriptionTransactionAdapter.setData(subscriptionTransactions)
            }
        }
    }

    private fun totalAmountOfTransactions() {
        val budgetAmount = transactions.filter { it.amount > 0 }.map { it.amount }.sum()
        val totalSubscriptionAmount = Math.abs(transactions.filter { it.type == "Subscriptions" }.map { it.amount }.sum())
        val subscriptionPercentage = if (budgetAmount != 0.0) {
            (totalSubscriptionAmount / budgetAmount) * 100
        } else {
            0.0
        }
        binding.percentageSubscription.text = "%.2f".format(subscriptionPercentage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        fetchSubsctiptionTransactions()
    }
}