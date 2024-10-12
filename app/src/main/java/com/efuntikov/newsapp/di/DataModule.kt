package com.efuntikov.newsapp.di

import android.content.Context
import androidx.room.Room
import com.efuntikov.newsapp.domain.repository.LocalStorage
import com.efuntikov.newsapp.domain.repository.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    fun provideLocalStorage(context: Context) = LocalStorage(context = context)

    @Provides
    fun provideDatabase(context: Context) =
        Room.databaseBuilder(context, NewsDatabase::class.java, "news-database").build()
}
