package com.example.basecompose.utils

import android.content.Context
import android.content.SharedPreferences

object GSharePreference {

    private var sharedPref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private const val PREFS_NAME = "base_compose_preferences"

    init {
        sharedPref = GSharePrefContext.instance?.applicationContext?.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = sharedPref?.edit()
    }

    fun put(
        key: String,
        value: String
    ) {
        editor?.putString(key, value)
            ?.commit()
    }

    fun put(
        key: String,
        value: Boolean
    ) {
        editor?.putBoolean(key, value)
            ?.commit()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPref?.getBoolean(key, false) ?: false
    }

    fun getString(key: String): String? {
        return sharedPref?.getString(key, null)
    }

    fun clear() {
        editor?.clear()
            ?.commit()
    }
}
