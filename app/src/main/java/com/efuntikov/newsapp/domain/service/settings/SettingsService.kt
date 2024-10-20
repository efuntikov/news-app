package com.efuntikov.newsapp.domain.service.settings

import com.efuntikov.newsapp.domain.datasource.Language
import kotlinx.coroutines.flow.Flow

interface SettingsService {
    suspend fun setLanguage(language: Language)
    fun availableLanguages(): List<Language>
    fun getLanguage(): Language
    fun observeLanguage(): Flow<Language>
}
