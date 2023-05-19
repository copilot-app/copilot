package com.copilot.data.access

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.sql.Types.NULL

private const val DATA_FILE = "tasks_file"

fun saveUserData(context: Context, key: String, value: Any ) {
    val jsonObjectString = Gson().toJson(value)
    val sharedPreferences = context.getSharedPreferences(DATA_FILE, Context.MODE_PRIVATE)
    sharedPreferences.edit().putString(key, jsonObjectString).apply()
}

fun <T: Any> getUserData(context: Context, key: String): Any? {
    val sharedPreferences = context.getSharedPreferences(DATA_FILE, Context.MODE_PRIVATE)
    val value = sharedPreferences.getString(key, "")

    if (value.isNullOrEmpty()) {
        return NULL
    }

    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(value, type)
}