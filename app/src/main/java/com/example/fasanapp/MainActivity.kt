package com.example.fasanapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.fasanapp.util.PreferenceKeys.USER
import com.example.fasanapp.models.User
import com.example.fasanapp.mvvm.UserViewModel
import com.example.fasanapp.ui.Navigator
import com.example.fasanapp.ui.theme.FasanAppTheme
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {
    private val viewModel: UserViewModel by viewModels()

    private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "user"
    )

    private fun useUser() {
        lifecycleScope.launchWhenStarted {
            //userPreferencesDataStore.edit { it.clear() } //Logout
            userPreferencesDataStore.data.collect {
                val usr = it[USER] ?: setOf("", "")
                viewModel.login(User(usr.first(), usr.last()))
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