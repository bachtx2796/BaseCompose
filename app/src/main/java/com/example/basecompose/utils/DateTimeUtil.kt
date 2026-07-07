package com.example.basecompose.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Get readable date
 *
 * @param pattern [String] default to "yyyy-MM-dd-HH:mm:ss"
 * @param locale [Locale] default to [Locale.getDefault]
 * @return [String] readable date
 */
fun Long.convertToReadableDate(
    pattern: String = "yyyy-MM-dd-HH:mm:ss",
    locale: Locale = Locale.getDefault()
): String {
    return SimpleDateFormat(pattern, locale).format(Date(this))
}
