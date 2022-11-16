package com.practice.newsapplication.data

import com.practice.newsapplication.models.ArticleCategory
import com.practice.newsapplication.models.Source
import com.practice.newsapplication.network.NewsManager

class Repository(val manager: NewsManager) {
    suspend fun getArticles() = manager.getArticles("us")
    suspend fun getArticlesByCategory(category: String) = manager.getArticleByCategory(category)
    suspend fun getArticlesBySource(source: String) = manager.getArticleBySource(source)
}