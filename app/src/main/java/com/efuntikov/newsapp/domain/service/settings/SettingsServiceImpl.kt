package com.efuntikov.newsapp.domain.service.settings

import android.content.Context
import android.content.res.Configuration
import com.efuntikov.newsapp.domain.repository.LocalStorage
import com.efuntikov.newsapp.domain.repository.NewsDatabase
import java.util.Locale
import javax.inject.Inject

class SettingsServiceImpl @Inject constructor(
    private val newsDatabase: NewsDatabase,
    private val localStorage: LocalStorage,
    private val context: Context
) : SettingsService {

    init {
        checkLanguage()
    }

    private fun checkLanguage() {
        if (localStorage.getLanguage().isEmpty()) {
            val configuration: Configuration = context.resources.configuration
            val locale: Locale = configuration.locales.get(0)

            localStorage.setLanguage(locale.language)
        }
    }

    override fun getLanguage() = localStorage.getLanguage()

    override fun observeLanguage() = localStorage.observeLanguage()
}
