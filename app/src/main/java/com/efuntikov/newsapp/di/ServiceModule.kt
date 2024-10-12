package com.efuntikov.newsapp.di

import com.efuntikov.newsapp.domain.service.news.NewsService
import com.efuntikov.newsapp.domain.service.news.NewsServiceImpl
import com.efuntikov.newsapp.domain.service.settings.SettingsService
import com.efuntikov.newsapp.domain.service.settings.SettingsServiceImpl
import com.efuntikov.newsapp.domain.service.tophead.TopHeadNewsService
import com.efuntikov.newsapp.domain.service.tophead.TopHeadNewsServiceImpl
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

    @Singleton
    @Binds
    abstract fun bindTopHeadNewsService(impl: TopHeadNewsServiceImpl): TopHeadNewsService

    @Singleton
    @Binds
    abstract fun bindSettingsService(impl: SettingsServiceImpl): SettingsService
}
