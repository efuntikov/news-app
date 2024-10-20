package com.efuntikov.newsapp.di

import com.efuntikov.newsapp.usecase.NewsUseCase
import com.efuntikov.newsapp.usecase.NewsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun bindNewsUseCase(impl: NewsUseCaseImpl): NewsUseCase
}
