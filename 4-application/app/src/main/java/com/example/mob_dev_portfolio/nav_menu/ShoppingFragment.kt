package com.example.mob_dev_portfolio.nav_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mob_dev_portfolio.budget_transaction.AppDatabase
import com.example.mob_dev_portfolio.budget_transaction.ShoppingTransactionAdapter
import com.example.mob_dev_portfolio.budget_transaction.Transaction
import com.example.mob_dev_portfolio.databinding.FragmentShoppingBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShoppingFragment : Fragment() {

    private var _binding: FragmentShoppingBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactions: List<Transaction>
    private lateinit var shoppingTransactions: List<Transaction>
    private lateinit var shoppingTransactionAdapter: ShoppingTransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        shoppingTransactions = ArrayList()
        shoppingTransactionAdapter = ShoppingTransactionAdapter(shoppingTransactions)
        linearLayoutManager = LinearLayoutManager(context)

        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "transactions").build()


        // Bind RecyclerView
        binding.shoppingRecyclerview.apply {
            adapter = shoppingTransactionAdapter
            layoutManager = linearLayoutManager
        }
        // Fetch food transactions
    }


    private fun fetchShoppingTransactions() {
        GlobalScope.launch {
            transactions = db.transactionDao().getAll()
            val shoppingTransactions = db.transactionDao().getAllTypeShopping()
            requireActivity().runOnUiThread {
                totalAmountOfTransactions()
                shoppingTransactionAdapter.setData(shoppingTransactions)
            }
        }
    }

    private fun totalAmountOfTransactions() {
        val budgetAmount = transactions.filter { it.amount > 0 }.map { it.amount }.sum()
        val totalShoppingAmount = Math.abs(transactions.filter { it.type == "Shopping" }.map { it.amount }.sum())
        val shoppingPercentage = if (budgetAmount != 0.0) {
            (totalShoppingAmount / budgetAmount) * 100
        } else {
            0.0
        }
        binding.percentageShopping.text = "%.2f".format(shoppingPercentage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        fetchShoppingTransactions()
    }
}