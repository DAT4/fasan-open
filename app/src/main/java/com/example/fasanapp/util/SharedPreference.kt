package com.example.fasanapp.util

import android.content.Context
import android.content.SharedPreferences
import com.example.fasanapp.models.User

class SharedPreference(val context: Context) {
    private val PREFS_NAME = "door_app"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(user: User) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putStringSet("user", setOf(user.username, user.password))
        editor.commit()
    }

    fun get(): User? {
        val data = sharedPref.getStringSet("user", null)
        data?.let {
            return User(data.first(), data.last())
        }
        return null
    }

}
