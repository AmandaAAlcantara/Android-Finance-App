package com.example.mob_dev_portfolio.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.mob_dev_portfolio.R
import com.example.mob_dev_portfolio.data.ValidateEmailBody
import com.example.mob_dev_portfolio.databinding.ActivityRegisterBinding
import com.example.mob_dev_portfolio.repository.AuthRepository
import com.example.mob_dev_portfolio.utils.APIService
import com.example.mob_dev_portfolio.view_model.RegisterActivityViewModel
import com.example.mob_dev_portfolio.view_model.RegisterActivityViewModelFactory
import java.lang.StringBuilder

class RegisterActivity: AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener {

    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mViewModel: RegisterActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.fullNameEt.onFocusChangeListener = this
        mBinding.emailEt.onFocusChangeListener = this
        mBinding.passwordEt.onFocusChangeListener = this
        mBinding.cPasswordEt.onFocusChangeListener = this
        mViewModel = ViewModelProvider(this, RegisterActivityViewModelFactory(AuthRepository(APIService.gerService()), application)).get(RegisterActivityViewModel::class.java)
        setupObservers()
    }

    private fun setupObservers(){
        mViewModel.getIsLoading().observe(this){
            mBinding.progressBar.isVisible = it

        }
        mViewModel.getIsUnique().observe(this) {
            if (validateEmail(shouldUpdateView = false)) {
                if (it) {
                    mBinding.emailTil.apply {
                        if (isErrorEnabled) isErrorEnabled = false
                        setStartIconDrawable(R.drawable.check_circle_24)
                        setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                    }
                } else {
                    mBinding.emailTil.apply {
                        if (startIconDrawable != null) startIconDrawable = null
                        isErrorEnabled = true
                        error = "Email is already taken"
                    }
                }
            }
        }

        mViewModel.getErrorMessage().observe(this) {
            //fullName, email, password
            val formErrorKeys = arrayListOf("fullName", "email", "password")
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains(entry.key)) {
                    when (entry.key) {
                        "fullName" -> {
                            mBinding.fullNameTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }

                        "email" -> {
                            mBinding.emailTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }

                        "password" -> {
                            mBinding.passwordTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                    }
                } else {
                    message.append(entry.value).append("\n")
                }
                if (message.isNotEmpty()) {
                    AlertDialog.Builder(this)
                        .setIcon(R.drawable.info_24)
                        .setTitle("INFORMATION")
                        .setMessage(message)
                        .setPositiveButton("OK") {dialog, _ -> dialog!!.dismiss() }
                        .show()

                }
            }
        }

        mViewModel.getUser().observe(this){

        }

    }

    private fun validateFullName(): Boolean{
        var errorMessage: String? = null
        val value: String = mBinding.fullNameEt.text.toString()
        if(value.isEmpty()){
            errorMessage = "Full name is required"
        }

        if(errorMessage != null){
            mBinding.fullNameTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validateEmail(shouldUpdateView: Boolean = true): Boolean{
        var errorMessage: String? = null
        val value = mBinding.emailEt.text.toString()
        if(value.isEmpty()){
            errorMessage = "Email is required"
        }else if(!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            errorMessage = "Email address is invalid"
        }

        if(errorMessage != null && shouldUpdateView){
            mBinding.emailTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }


        return errorMessage == null
    }

    private fun validatePassword(): Boolean{
        var errorMessage: String? = null
        val value = mBinding.passwordEt.text.toString()
        if(value.isEmpty()){
            errorMessage = "Password is required"
        }else if(value.length < 5){
            errorMessage = "Password must be 5 characters long"
        }

        if(errorMessage != null){
            mBinding.passwordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validateConfirmPassword() : Boolean{
        var errorMessage: String? = null
        val value = mBinding.cPasswordEt.text.toString()
        if(value.isEmpty()){
            errorMessage = "Confirm password is required"
        }else if(value.length < 5){
            errorMessage = "Confirm password must be 5 characters long"
        }

        if(errorMessage != null){
            mBinding.cPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validatePasswordAndConfirmPassword(): Boolean{
        var errorMessage: String? = null
        val password = mBinding.passwordEt.text.toString()
        val confirmPassword = mBinding.cPasswordEt.text.toString()
        if(password != confirmPassword){
            errorMessage = "Conform password does not match with password"
        }
        if(errorMessage != null){
            mBinding.cPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null


    }

    override fun onClick(view: View?) {

    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if(view != null){
            when(view.id){
                R.id.fullNameEt -> {
                    if(hasFocus){
                        if(mBinding.fullNameTil.isErrorEnabled){
                            mBinding.fullNameTil.isErrorEnabled = false
                        }
                    }else{
                        validateFullName()
                    }
                }

                R.id.emailEt -> {
                    if(hasFocus){
                        if(mBinding.emailTil.isErrorEnabled){
                            mBinding.emailTil.isErrorEnabled = false
                        }
                    }else{
                        if(validateEmail()){
                            mViewModel.validateEmailAddress(ValidateEmailBody(mBinding.emailEt.text!!.toString()))
                        }
                    }
                }

                R.id.passwordEt -> {
                    if(hasFocus){
                        if(mBinding.passwordTil.isErrorEnabled){
                            mBinding.passwordTil.isErrorEnabled = false
                        }
                    }else{
                        if(validatePassword() && mBinding.cPasswordEt.text!!.isNotEmpty() && validateConfirmPassword() && validatePasswordAndConfirmPassword()){
                            if(mBinding.cPasswordTil.isErrorEnabled){
                                mBinding.cPasswordTil.isErrorEnabled = false
                            }
                            mBinding.cPasswordTil.apply {
                                setStartIconDrawable(R.drawable.check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }
                }

                R.id.cPasswordEt -> {
                    if(hasFocus){
                        if(mBinding.cPasswordTil.isErrorEnabled){
                            mBinding.cPasswordTil.isErrorEnabled = false
                        }
                    }else{
                        if(validateConfirmPassword() && validatePassword() && validatePasswordAndConfirmPassword()){
                            if(mBinding.passwordTil.isErrorEnabled){
                                mBinding.passwordTil.isErrorEnabled = false
                            }
                            mBinding.cPasswordTil.apply {
                                setStartIconDrawable(R.drawable.check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }
                }

            }
        }

    }

    override fun onKey(view: View?, event: Int, keyEvent: KeyEvent?): Boolean {
        return false
    }
}
