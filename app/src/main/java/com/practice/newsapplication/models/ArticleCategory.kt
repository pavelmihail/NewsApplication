package com.practice.newsapplication.models

import com.practice.newsapplication.models.ArticleCategory.*

enum class ArticleCategory(val categoryName: String) {
    BUSINESS(categoryName = "Business"),
    ENTERTAIMENT(categoryName = "Entertainment"),
    GENERAL(categoryName = "General"),
    HEALTH(categoryName = "Health"),
    SCIENCE(categoryName = "Science"),
    SPORT(categoryName = "Sports"),
    TECHNOLOGY(categoryName = "Technology"),
}

fun getAllArticleCategories(): List<ArticleCategory> {
    return listOf(
        ArticleCategory.BUSINESS,
        ArticleCategory.ENTERTAIMENT,
        ArticleCategory.GENERAL,
        ArticleCategory.HEALTH,
        ArticleCategory.SCIENCE,
        ArticleCategory.SPORT,
        ArticleCategory.TECHNOLOGY
    )
}

fun getArticleCategory(category: String) : ArticleCategory? {
    val map = values().associateBy(ArticleCategory::categoryName)
    return map[category]
}