package com.copilot.data.access

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val DATA_FILE = "SharedPreferences"

fun saveUserData(context: Context, key: String, value: Any ) {
    val jsonObjectString = Gson().toJson(value)
    val sharedPreferences = context.getSharedPreferences(DATA_FILE, Context.MODE_PRIVATE)
    sharedPreferences.edit().putString(key, jsonObjectString).apply()
}

fun <T: Any> getUserData(context: Context, key: String): T? {
    val sharedPreferences = context.getSharedPreferences(DATA_FILE, Context.MODE_PRIVATE)
    val value = sharedPreferences.getString(key, "")

    if (value.isNullOrEmpty()) {
        return null
    }

    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(value, type)
}