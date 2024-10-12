package com.efuntikov.newsapp.domain.service.datasource

import com.efuntikov.newsapp.component.tophead.TopNewsCategory
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import com.efuntikov.newsapp.domain.service.news.NewsCallback
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import timber.log.Timber
import javax.inject.Inject

enum class Language {
    EN
}

private class TopHeadlinesCallback(
    private val newsCallback: NewsCallback,
    private val category: TopNewsCategory
) : NewsApiClient.ArticlesResponseCallback {
    override fun onSuccess(articleResponse: ArticleResponse?) {
        articleResponse?.let { response ->
            response.articles?.let { articles ->
                newsCallback.onSuccess(result = articles.map { article ->
                    NewsItemEntity(
                        title = article.title,
                        textContent = article.description ?: "",
                        author = article.author,
                        imageUrl = article.urlToImage,
                        category = category.name.lowercase()
                    )
                })
            }
        }
    }

    override fun onFailure(throwable: Throwable?) {
        newsCallback.onFailure(throwable)
    }
}

class NewsApiDataSource @Inject constructor() : NewsDataSource {
    private var newsApiClient: NewsApiClient = NewsApiClient("e16232bb16d740e1859e564d58218e55")

    override fun getTopHeadlines(
        newsCallback: NewsCallback,
        category: TopNewsCategory,
        pageSize: Int,
        language: Language
    ) = newsApiClient.getTopHeadlines(
        TopHeadlinesRequest.Builder()
            .category(category.name.lowercase())
            .language(language.name.lowercase())
            .pageSize(pageSize)
            .build(), TopHeadlinesCallback(newsCallback, category)
    )

    override fun getEverything(
        newsCallback: NewsCallback, pageSize: Int,
        language: Language
    ) = newsApiClient.getEverything(
        EverythingRequest.Builder()
            .language(language.name.lowercase())
            .pageSize(pageSize)
            .q("bitcoin")
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
                                imageUrl = article.urlToImage,
                                category = null
                            )
                        })
                    }
                }
            }

            override fun onFailure(throwable: Throwable?) {
                Timber.e(throwable, "Failed to fetch non-categorized news")
            }

        })

}
