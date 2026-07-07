package com.example.basecompose.utils

import android.content.Context

class GSharePrefContext private constructor(context: Context) {

    var applicationContext: Context? = context

    fun setContext(context: Context?) {
        applicationContext = context
    }

    companion object {
        var instance: GSharePrefContext? = null
            private set

        fun init(context: Context) {
            instance = GSharePrefContext(context)
        }
    }
}
