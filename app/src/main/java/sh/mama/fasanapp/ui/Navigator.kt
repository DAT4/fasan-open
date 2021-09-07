package sh.mama.fasanapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import sh.mama.fasanapp.api.Resource
import sh.mama.fasanapp.mvvm.UserViewModel

@Composable
fun Navigator(vm: UserViewModel) {
    val token by vm.token.collectAsState()
    when (token) {
        is Resource.Loading -> {
            LoadingScreen()
        }
        is Resource.Success -> {
            OpenDoorScreen(vm = vm)
        }
        is Resource.Error -> {
            LoginScreen(vm = vm)
        }
    }
}