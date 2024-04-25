package com.example.mob_dev_portfolio.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.mob_dev_portfolio.databinding.ActivityUserLoginBinding
import com.google.firebase.auth.FirebaseAuth

class UserLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserLoginBinding

    private lateinit var actionBar: ActionBar

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Corrected LayoutInflater usage:
        binding = ActivityUserLoginBinding.inflate(layoutInflater) // Use layoutInflater, not LayoutInflater
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Profile"

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
    }

    private fun checkUser(){
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            val email = firebaseUser.email
            binding.emailTv.text = email
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}