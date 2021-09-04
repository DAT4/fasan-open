package com.example.fasanapp.api

import com.example.fasanapp.models.Token
import com.example.fasanapp.models.User
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*


interface API {
    @POST("/token")
    @FormUrlEncoded
    suspend fun auth(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<Token>
    @GET("/door")
    suspend fun open(@Header("Authorization") token : String) : Response<Any>
}