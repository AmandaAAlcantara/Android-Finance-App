package com.example.mob_dev_portfolio.budget_transaction

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val type: String,
    val label: String,
    val amount: Double,
    val description: String): Serializable{
}

