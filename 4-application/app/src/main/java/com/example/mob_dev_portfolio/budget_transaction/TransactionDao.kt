package com.example.mob_dev_portfolio.budget_transaction

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TransactionDao {

    //get all values from the room database
    @Query("SELECT * from transactions")
    fun getAll(): List<Transaction>


    //get all Food values from room database
    @Query("SELECT * FROM transactions WHERE type = 'Food'")
    fun getAllTypeFood(): List<Transaction>
    @Query("SELECT COUNT(*) FROM transactions WHERE type = 'Food'")
    fun getSumTypeFood(): List<Transaction>


    //get all Savings values from room database
    @Query("SELECT * FROM transactions WHERE type = 'Savings'")
    fun getAllTypeSavings(): List<Transaction>
    @Query("SELECT COUNT(*) FROM transactions WHERE type = 'Savings'")
    fun getSumTypeSavings(): List<Transaction>


    //get all Shopping values from room database
    @Query("SELECT * FROM transactions WHERE type = 'Shopping'")
    fun getAllTypeShopping(): List<Transaction>
    @Query("SELECT COUNT(*) FROM transactions WHERE type = 'Shopping'")
    fun getSumTypeShopping(): List<Transaction>


    //get all Subscriptions values from room database
    @Query("SELECT * FROM transactions WHERE type = 'Subscriptions'")
    fun getAllTypeSubscriptions(): List<Transaction>
    @Query("SELECT COUNT(*) FROM transactions WHERE type = 'Subscriptions'")
    fun getSumTypeSubscriptions(): List<Transaction>


    //get all Transportation values from room database
    @Query("SELECT * FROM transactions WHERE type = 'Transportation'")
    fun getAllTypeTransportation(): List<Transaction>
    @Query("SELECT COUNT(*) FROM transactions WHERE type = 'Transportation'")
    fun getSumTypeTransportation(): List<Transaction>


    //get all Utilities values from room database
    @Query("SELECT * FROM transactions WHERE type = 'Utilities'")
    fun getAllTypeUtilities(): List<Transaction>
    @Query("SELECT COUNT(*) FROM transactions WHERE type = 'Utilities'")
    fun getSumTypeUtilities(): List<Transaction>


    //insert transaction
    @Insert
    fun insertAll(vararg transaction: Transaction)


    //delete transaction
    @Delete
    fun delete(transaction: Transaction)


    //update a transaction
    @Update
    fun update(vararg  transaction: Transaction)
}