package com.efuntikov.newsapp.domain.service.news

import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse

class NewsApiDataSource {
    private var newsApiClient: NewsApiClient = NewsApiClient("e16232bb16d740e1859e564d58218e55")

    fun getTopHeadlines(newsCallback: NewsCallback) = newsApiClient.getTopHeadlines(
        TopHeadlinesRequest.Builder()
            .q("tesla")
            .language("en")
            .pageSize(20)
            .build(),
        object : NewsApiClient.ArticlesResponseCallback {
            override fun onSuccess(articleResponse: ArticleResponse?) {
                articleResponse?.let { response ->
                    response.articles?.let { articles ->
                        newsCallback.onSuccess(result = articles.map { article ->
                            NewsItemEntity(
                                title = article.title,
                                textContent = article.description,
                                author = article.author,
                                imageUrl = article.urlToImage
                            )
                        })
                    }
                }
            }

            override fun onFailure(throwable: Throwable?) {
                TODO("Not yet implemented")
            }

        })

    fun getEverything(newsCallback: NewsCallback) = newsApiClient.getEverything(
        EverythingRequest.Builder()
            .q("tesla")
            .language("en")
            .pageSize(20)
            .build(),
        object : NewsApiClient.ArticlesResponseCallback {
            override fun onSuccess(articleResponse: ArticleResponse?) {
                articleResponse?.let { response ->
                    response.articles?.let { articles ->
                        newsCallback.onSuccess(result = articles.map { article ->
                            NewsItemEntity(
                                title = article.title,
                                textContent = article.description,
                                author = article.author,
                                imageUrl = article.urlToImage
                            )
                        })
                    }
                }
            }

            override fun onFailure(throwable: Throwable?) {
                TODO("Not yet implemented")
            }

        })

}
