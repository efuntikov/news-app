package com.efuntikov.newsapp.di

import com.efuntikov.newsapp.domain.service.datasource.NewsApiDataSource
import com.efuntikov.newsapp.domain.service.datasource.NewsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindNewsApiDataSource(impl: NewsApiDataSource): NewsDataSource
}
