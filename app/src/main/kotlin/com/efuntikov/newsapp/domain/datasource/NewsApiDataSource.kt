package com.efuntikov.newsapp.domain.datasource

import com.efuntikov.newsapp.component.tophead.TopNewsCategory
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import com.efuntikov.newsapp.domain.service.news.NewsCallback
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import timber.log.Timber
import javax.inject.Inject

enum class Language(val description: String) {
    AR("Arabic"),
    DE("German"),
    EN("English"),
    ES("Spanish"),
    FR("French"),
    HE("Hebrew"),
    IT("Italian"),
    NL("Dutch"),
    NO("Norwegian"),
    PT("Portuguese"),
    RU("Russian"),
    SV("Swedish"),
    UD("Ukrainian"),
    ZH("Chinese");
}

fun String.mapFromLocale() = when (this) {
    "ar" -> Language.AR
    "de" -> Language.DE
    "en" -> Language.EN
    "es" -> Language.ES
    "fr" -> Language.FR
    "he" -> Language.HE
    "it" -> Language.IT
    "nl" -> Language.NL
    "no" -> Language.NO
    "pt" -> Language.PT
    "ru" -> Language.RU
    "sv" -> Language.SV
    "uk" -> Language.UD
    "zh" -> Language.ZH
    else -> Language.EN
}

private class TopHeadlinesCallback(
    private val newsCallback: NewsCallback,
    private val category: TopNewsCategory
) : NewsApiClient.ArticlesResponseCallback {
    override fun onSuccess(articleResponse: ArticleResponse?) {
        articleResponse?.let { response ->
            response.articles?.let { articles ->
                newsCallback.onSuccess(result = articles
                    .filterRemoved()
                    .map { article ->
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

private fun MutableList<Article>.filterRemoved() = filter { article ->
    !article.title.contains(
        "[Removed]"
    )
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
        newsCallback: NewsCallback, pageSize: Int, page: Int,
        language: Language
    ) = newsApiClient.getEverything(
        EverythingRequest.Builder()
            .language(language.name.lowercase())
            .pageSize(pageSize)
            .page(page)
            .q("bitcoin")
            .build(),
        object : NewsApiClient.ArticlesResponseCallback {
            override fun onSuccess(articleResponse: ArticleResponse?) {
                articleResponse?.let { response ->
                    response.articles?.let { articles ->
                        newsCallback.onSuccess(result = articles.filterRemoved().map { article ->
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
