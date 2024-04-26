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
import com.example.mob_dev_portfolio.budget_transaction.TransportationTransactionAdapter
import com.example.mob_dev_portfolio.databinding.FragmentTransportBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TransportFragment : Fragment() {

    private var _binding: FragmentTransportBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactions: List<Transaction>
    private lateinit var transportTransactions: List<Transaction>
    private lateinit var transportTransactionAdapter: TransportationTransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        transportTransactions = ArrayList()
        transportTransactionAdapter = TransportationTransactionAdapter(transportTransactions)
        linearLayoutManager = LinearLayoutManager(context)

        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "transactions").build()


        // Bind RecyclerView
        binding.transportationRecyclerview.apply {
            adapter = transportTransactionAdapter
            layoutManager = linearLayoutManager
        }
        // Fetch food transactions
    }


    private fun fetchTransportationTransactions() {
        GlobalScope.launch {
            transactions = db.transactionDao().getAll()
            val transportationTransactions = db.transactionDao().getAllTypeTransportation()
            requireActivity().runOnUiThread {
                totalAmountOfTransactions()
                transportTransactionAdapter.setData(transportationTransactions)
            }
        }
    }

    private fun totalAmountOfTransactions() {
        val budgetAmount = transactions.filter { it.amount > 0 }.map { it.amount }.sum()
        val totalTransportationAmount = Math.abs(transactions.filter { it.type == "Transportation" }.map { it.amount }.sum())
        val transportationPercentage = if (budgetAmount != 0.0) {
            (totalTransportationAmount / budgetAmount) * 100
        } else {
            0.0
        }
        binding.percentageTransportation.text = "%.2f".format(transportationPercentage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        fetchTransportationTransactions()
    }
}
