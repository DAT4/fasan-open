package com.example.fasanapp.mvvm

import com.example.fasanapp.api.Resource
import com.example.fasanapp.api.RetrofitInstance
import com.example.fasanapp.models.Token
import com.example.fasanapp.models.User
import retrofit2.Response

class UserRepository {
    suspend fun auth(user: User): Resource<Token> =
        handleResponse(RetrofitInstance.api.auth(user.username, user.password))

    private fun handleResponse(r: Response<Token>): Resource<Token> {
        if (r.isSuccessful) {
            r.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error("Not logged in")
    }

    suspend fun open(token: Token): Boolean =
        RetrofitInstance.api.open(token = "Bearer ${token.access_token}").isSuccessful
}