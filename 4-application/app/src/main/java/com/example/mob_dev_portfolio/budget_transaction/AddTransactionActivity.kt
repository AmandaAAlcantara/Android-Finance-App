package com.example.mob_dev_portfolio.budget_transaction

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.mob_dev_portfolio.BuildConfig
import com.example.mob_dev_portfolio.R
import com.example.mob_dev_portfolio.databinding.ActivityAddTransactionBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddTransactionActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAddTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = listOf("Food", "Savings", "Shopping", "Subscriptions", "Transportation", "Utilities")
        val autoComplete: AutoCompleteTextView = findViewById(R.id.auto_complete_txt)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, type)
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val typeSelected = adapterView.getItemAtPosition(i)
        }

        binding.labelInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length ?: 0 > 0) {
                    binding.labelLayout.error = null
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.amountInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length ?: 0 > 0) {
                    binding.amountLayout.error = null
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.addTransactionBtn.setOnClickListener {
            val type = binding.autoCompleteTxt.text.toString()
            val label = binding.labelInput.text.toString()
            val description = binding.descriptionInput.text.toString()
            val amount = binding.amountInput.text.toString().toDoubleOrNull()


            if (label.isEmpty()) {
                binding.labelLayout.error = "Please enter a valid label"
            }

            else if (amount == null) {
                binding.amountLayout.error = "Please enter a valid amount"
            }
            else {
                val transaction = Transaction(0, type, label, amount, description)
                insert(transaction)
            }
        }

        binding.closeButton.setOnClickListener{
            finish()
        }

    }

    private fun insert(transaction: Transaction){
        val db = Room.databaseBuilder(this,
            AppDatabase::class.java,
            "transactions").build()

//        private fun insert(transaction: Transaction) {
//            val db = DatabaseBuilder.getInstance(applicationContext)
//            db.transactionDao().insert(transaction)
//        }

        GlobalScope.launch {
            db.transactionDao().insertAll(transaction)
            finish()
        }
    }
}