package com.example.fasanapp.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fasanapp.api.Resource
import com.example.fasanapp.models.Token
import com.example.fasanapp.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class UserViewModel() : ViewModel() {
    private val userRepository = UserRepository()
    private val _token = MutableStateFlow<Resource<Token>>(Resource.Loading())
    val token: StateFlow<Resource<Token>> = _token
    private val _open = MutableStateFlow(false)
    val open: StateFlow<Boolean> = _open

    fun login(user: User) = viewModelScope.launch {
        _token.value = Resource.Loading()
        _token.value = userRepository.auth(user)
    }

    fun open() = viewModelScope.launch {
        _open.value = true
        token.value.data?.let {
            userRepository.open(it)
        }
        _open.value = false
    }

}