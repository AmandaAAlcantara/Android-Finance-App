package com.example.mob_dev_portfolio.utils

import com.example.mob_dev_portfolio.data.UniqueEmailValidationResponse
import com.example.mob_dev_portfolio.data.ValidateEmailBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIConsumer {

    @POST("users/validate-unique-email")
    suspend fun validateEmailAddress(@Body body: ValidateEmailBody) : Response<UniqueEmailValidationResponse>
}