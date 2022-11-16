package com.practice.newsapplication.network

import android.util.Log
import androidx.compose.runtime.*
import com.practice.newsapplication.models.ArticleCategory
import com.practice.newsapplication.models.TopNewsResponse
import com.practice.newsapplication.models.getArticleCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager(private val service: NewsService) {

    private val _newsResponse = mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _newsResponse
        }

    val sourceName = mutableStateOf("abc-news")
    private val _getArticleBySource = mutableStateOf(TopNewsResponse())
    val getArticleSource: State<TopNewsResponse>
        @Composable get() = remember {
            _getArticleBySource
        }

    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)

    suspend fun getArticles(country: String): TopNewsResponse
    = withContext(Dispatchers.IO) {

        service.getTopArticles(country = country)
    }

    suspend fun getArticleByCategory(category: String) : TopNewsResponse
    = withContext(Dispatchers.IO) {

        service.getArticlesByCategory(category = category)
    }

    suspend fun getArticleBySource(source: String): TopNewsResponse = withContext(Dispatchers.IO) {

        service.getArticlesBySources(source)
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category)
        selectedCategory.value = newCategory
    }
}