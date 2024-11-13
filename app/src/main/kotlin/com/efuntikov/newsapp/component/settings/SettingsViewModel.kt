package com.efuntikov.newsapp.component.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.efuntikov.newsapp.component.BaseViewModel
import com.efuntikov.newsapp.domain.datasource.Language
import com.efuntikov.newsapp.domain.datasource.mapFromLocale
import com.efuntikov.newsapp.domain.service.settings.SettingsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsService: SettingsService
) : BaseViewModel() {
    val languageSelected = mutableStateOf(settingsService.getLanguage())
    val availableLanguagesList = mutableStateOf(emptyList<Language>())

    fun load() {
        availableLanguagesList.value = settingsService.availableLanguages()

        viewModelScope.launch {
            settingsService.observeLanguage().cancellable().collect { selectedLang ->
                languageSelected.value = selectedLang
            }
        }
    }

    fun setLanguage(lang: Language) {
        viewModelScope.launch {
            settingsService.setLanguage(lang)
        }
    }
}
