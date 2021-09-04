package com.example.fasanapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.fasanapp.PreferenceKeys.USER
import com.example.fasanapp.models.User
import com.example.fasanapp.mvvm.UserViewModel
import com.example.fasanapp.ui.LoginScreen
import com.example.fasanapp.ui.Navigator
import com.example.fasanapp.ui.theme.FasanAppTheme
import kotlinx.coroutines.flow.collect

object PreferenceKeys {
    val USER = stringSetPreferencesKey("user")
}

class MainActivity : ComponentActivity() {
    private val viewModel: UserViewModel by viewModels()

    private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "user"
    )

    private fun useUser() {
        lifecycleScope.launchWhenStarted {
            userPreferencesDataStore.data.collect {
                it[USER]?.let { usr ->
                    viewModel.login(User(usr.first(), usr.last()))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        useUser()
        setContent {
            FasanAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Navigator(vm = viewModel)
                }
            }
        }
    }
}