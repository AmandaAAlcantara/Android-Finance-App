package com.example.mob_dev_portfolio.repository

import com.example.mob_dev_portfolio.data.UniqueEmailValidationResponse
import com.example.mob_dev_portfolio.data.ValidateEmailBody
import com.example.mob_dev_portfolio.utils.APIConsumer
import com.example.mob_dev_portfolio.utils.RequestStatus
import com.example.mob_dev_portfolio.utils.SimplifiedMessage
import kotlinx.coroutines.flow.flow


class AuthRepository(private val consumer: APIConsumer) {
    fun validateEmailAddress(body: ValidateEmailBody) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.validateEmailAddress(body)
        if(response.isSuccessful){
            emit(RequestStatus.Success(response.body()!!))

        }else{
            emit(RequestStatus.Error(SimplifiedMessage.get(response.errorBody()!!.byteStream().reader().readText())))
        }



    }
}