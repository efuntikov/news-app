package com.efuntikov.newsapp.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindContext(impl: Application): Context
}
