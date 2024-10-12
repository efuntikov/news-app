package com.efuntikov.newsapp.domain.service.settings

import kotlinx.coroutines.flow.Flow

interface SettingsService {
    fun getLanguage(): String
    fun observeLanguage(): Flow<String>
}
