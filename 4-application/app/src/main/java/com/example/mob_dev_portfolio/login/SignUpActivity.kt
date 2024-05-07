package com.example.mob_dev_portfolio.login

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.mob_dev_portfolio.MainActivity
import com.example.mob_dev_portfolio.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth


class SignUpActivity : AppCompatActivity() {
    //view binding

    private lateinit var binding: ActivitySignUpBinding

    //Action bar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog :ProgressDialog
    //Firebase Auth
    private lateinit var firebaseAuth :FirebaseAuth
    private var email = ""
    private var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //config ACTIONBAR
        actionBar = supportActionBar!!
        actionBar.title ="Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)


        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Pls wait")
        progressDialog.setMessage("Creating Account...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.SignUpBtn.setOnClickListener{
            validateData()
        }
    }
    private fun validateData(){
        email=binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.emailEt.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            //no password is entered
            binding.passwordEt.error = "Pls enter password "
        }
        else if  (password.length <6) {
            binding.passwordEt.error = "Password must at least be 6 charc long"
        }
        else {
            firebaseSignUp()
        }


    }
    private fun firebaseSignUp() {
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                Toast.makeText(this, "Account created with email ${firebaseUser?.email}", Toast.LENGTH_SHORT).show()

                // Navigate to profile activity or main activity after sign up
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "SignUp Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        // Check if user is logged in
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            // User is already logged in, navigate to profile activity
            startActivity(Intent(this, UserLoginActivity::class.java))
            finish()
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}