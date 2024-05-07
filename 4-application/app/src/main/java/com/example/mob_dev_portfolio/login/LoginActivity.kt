package com.example.mob_dev_portfolio.login

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.mob_dev_portfolio.MainActivity
import com.example.mob_dev_portfolio.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityLoginBinding
    //ActionBar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog:ProgressDialog
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Login"
            setDisplayHomeAsUpEnabled(true)  // Enable the Up button
        }
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()


        //connfigure actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Login"

        // config progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Pls wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseauth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, begin SignUpActivity
        binding.noAccountTv.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        //handle click, begin login
        binding.loginBtn.setOnClickListener{ //before logging in validate the data
            validateData()
        }

    }
    private fun validateData() {
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        Log.d("AuthDebug", "Email: $email, Password: $password")  // Add this to debug

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEt.error = "Invalid email format"
        } else if (TextUtils.isEmpty(password)) {
            binding.passwordEt.error = "Please enter password"
        } else {
            firebaseLogin()
        }
    }


    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Log.e("LoginError", "Login failed due to ${e.message}")
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()  // This will navigate back to MainActivity or the previous activity
        return true

    }
    private fun checkUser(){
        //if user logs in go to profile activity
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            //user is already logged in
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}