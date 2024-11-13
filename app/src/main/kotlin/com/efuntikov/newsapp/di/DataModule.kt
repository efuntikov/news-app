package com.efuntikov.newsapp.di

import android.content.Context
import androidx.room.Room
import com.efuntikov.newsapp.domain.repository.LocalStorage
import com.efuntikov.newsapp.domain.repository.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideLocalStorage(context: Context) = LocalStorage(context = context)

    @Provides
    @Singleton
    fun provideDatabase(context: Context) =
        Room.databaseBuilder(context, NewsDatabase::class.java, "news-database")
            .fallbackToDestructiveMigration() // TODO: Add migrations
            .build()
}
