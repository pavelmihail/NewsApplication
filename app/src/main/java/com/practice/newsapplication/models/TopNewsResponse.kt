package com.practice.newsapplication.models

data class TopNewsResponse(
    val status: String? = null,
    val totalResults: Int? =null,
    val articles: List<TopNewsArticles>? = null
)
