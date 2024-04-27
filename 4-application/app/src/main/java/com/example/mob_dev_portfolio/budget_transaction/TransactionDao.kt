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


    //get all Shopping values from room database
    @Query("SELECT * FROM transactions WHERE type = 'Shopping'")
    fun getAllTypeShopping(): List<Transaction>



    //get all Subscriptions values from room database
    @Query("SELECT * FROM transactions WHERE type = 'Subscriptions'")
    fun getAllTypeSubscriptions(): List<Transaction>



    //get all Transportation values from room database
    @Query("SELECT * FROM transactions WHERE type = 'Transportation'")
    fun getAllTypeTransportation(): List<Transaction>



    //get all Utilities values from room database
    @Query("SELECT * FROM transactions WHERE type = 'Utilities'")
    fun getAllTypeUtilities(): List<Transaction>

    //get all Income values from room database
    @Query("SELECT * FROM transactions WHERE type = 'Income'")
    fun getAllTypeIncome(): List<Transaction>



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