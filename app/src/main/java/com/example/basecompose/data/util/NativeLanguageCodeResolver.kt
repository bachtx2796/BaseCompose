package com.example.basecompose.data.util

import com.example.basecompose.utils.GSharePreference

object NativeLanguageCodeResolver {

    private const val NATIVE_LANGUAGE_KEY = "native_language"
    private const val DEFAULT_LANGUAGE_CODE = "vi"

    private val codeNameToIso =
        mapOf(
            "Vietnamese" to "vi",
            "English" to "en",
            "Japanese" to "ja",
            "Korean" to "ko",
            "French" to "fr",
            "German" to "de",
            "Spanish" to "es",
            "Russian" to "ru",
            "Thai" to "th",
            "Chinese" to "zh",
        )

    fun resolve(): String {
        val storedName = GSharePreference.getString(NATIVE_LANGUAGE_KEY).orEmpty()
        return codeNameToIso.entries
            .firstOrNull { it.key.equals(storedName, ignoreCase = true) }
            ?.value
            ?: DEFAULT_LANGUAGE_CODE
    }
}
