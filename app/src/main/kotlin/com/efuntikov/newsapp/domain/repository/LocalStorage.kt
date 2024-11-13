package com.efuntikov.newsapp.domain.repository

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@InstallIn(SingletonComponent::class)
@EntryPoint
interface LocalStorageEntryPoint {
    fun localStorage(): LocalStorage
}

private inline fun <reified T : Any> makePreferencesListener(
    listenToKey: String,
    crossinline callback: (T) -> Unit
) = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
    if (listenToKey == key) {
        when (T::class) {
            Boolean::class -> callback.invoke(sharedPreferences.getBoolean(key, false) as T)
            String::class -> callback.invoke(sharedPreferences.getString(key, "") as T)
            Int::class -> callback.invoke(sharedPreferences.getInt(key, 0) as T)
            Float::class -> callback.invoke(sharedPreferences.getFloat(key, 0f) as T)
            Long::class -> callback.invoke(sharedPreferences.getLong(key, 0L) as T)
        }
    }
}

private inline fun <reified T : Any> makeCallbackFlow(key: String, preferences: SharedPreferences) =
    callbackFlow {
        makePreferencesListener<T>(
            listenToKey = key,
            callback = { trySend(it) }).let { listener ->
            preferences.registerOnSharedPreferenceChangeListener(listener)
            awaitClose { preferences.unregisterOnSharedPreferenceChangeListener(listener) }
        }
    }

class LocalStorage @Inject constructor(private val context: Context) {

    private val systemPrefs: SharedPreferences by lazy {
        buildLocalStorage(context, "system_prefs")
    }


    private fun buildLocalStorage(context: Context, storageName: String): SharedPreferences {
        val spec = KeyGenParameterSpec.Builder(
            MasterKey.DEFAULT_MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
            .build()

        val masterKeyAlias = MasterKey.Builder(context).setKeyGenParameterSpec(spec).build()

        return EncryptedSharedPreferences.create(
            context,
            storageName,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun setLanguage(langTag: String) {
        systemPrefs.edit().putString("lang_tag", langTag).apply()
    }

    fun getLanguage() = systemPrefs.getString("lang_tag", "")!!

    fun observeLanguage(): Flow<String> =
        makeCallbackFlow(key = "lang_tag", preferences = systemPrefs)
}
