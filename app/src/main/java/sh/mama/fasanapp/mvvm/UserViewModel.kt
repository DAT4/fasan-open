package sh.mama.fasanapp.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import sh.mama.fasanapp.api.Resource
import sh.mama.fasanapp.models.Token
import sh.mama.fasanapp.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import sh.mama.fasanapp.DataStoreManager
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val localData: DataStoreManager
) : ViewModel() {
    private val userRepository = UserRepository()

    private val _token = MutableStateFlow<Resource<Token>>(Resource.Loading())
    private val _open = MutableStateFlow(false)

    val token: StateFlow<Resource<Token>> = _token
    val open: StateFlow<Boolean> = _open

    init {
        getUser()
    }

    private suspend fun login(user: User) {
        _token.value = Resource.Loading()
        _token.value = userRepository.auth(user)
    }

    private fun getUser() = viewModelScope.launch {
        localData.data.let { data ->
            data.collect { user ->
                when (user){
                    is Resource.Success -> user.data?.let { login(it) }
                    else -> _token.value = Resource.Error("No user saved")
                }
            }
        }
    }

    fun manualLogin(user: User) = viewModelScope.launch {
        login(user)
        if (token.value is Resource.Success) {
            localData.putData(user)
        }
    }

    fun open() = viewModelScope.launch {
        _open.value = true
        token.value.data?.let {
            userRepository.open(it)
        }
        _open.value = false
    }

}