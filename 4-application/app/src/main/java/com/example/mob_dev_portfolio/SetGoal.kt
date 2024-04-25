package com.example.mob_dev_portfolio

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.mob_dev_portfolio.databinding.ActivityAddTransactionBinding
import com.example.mob_dev_portfolio.databinding.ActivitySetGoalBinding

class SetGoal: AppCompatActivity() {
    private lateinit var binding: ActivitySetGoalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}