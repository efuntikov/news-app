package com.efuntikov.newsapp.di

import com.efuntikov.newsapp.domain.service.news.NewsService
import com.efuntikov.newsapp.domain.service.news.NewsServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ServiceModule {

    @Singleton
    @Binds
    abstract fun bindNewsService(impl: NewsServiceImpl): NewsService
}
