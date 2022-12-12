package com.learning.data.core

import android.os.Environment
import io.michaelrocks.paranoid.Obfuscate
import java.io.File

@Obfuscate
object Constants {
    const val DATE_PATTERN_DEFAULT_LOCAL = "yyyy-MM-dd"
    const val DATE_PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss.SSS"
    const val BASE_URL = "https://www.omdbapi.com/"
    const val API_KEY = "ae9837c2"

}