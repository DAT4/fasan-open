package com.example.fasanapp.models

data class User(
    val username: String,
    val password: String
) {
    fun toSet() = setOf(username, password)
}

