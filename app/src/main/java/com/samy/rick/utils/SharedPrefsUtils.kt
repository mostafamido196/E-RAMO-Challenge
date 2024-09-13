package com.samy.rick.utils

import android.content.Context

object SharedPrefsUtils {
    private const val PREFS_NAME = "app_prefs"
    private const val KEY_LAST_EMAIL = "last_email"

    fun saveLastEmail(context: Context, email: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_LAST_EMAIL, email)
        editor.apply() // Asynchronous save
    }

    fun getLastEmail(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_LAST_EMAIL, "")
    }
}
