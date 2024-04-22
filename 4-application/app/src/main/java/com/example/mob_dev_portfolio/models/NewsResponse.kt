package com.example.mob_dev_portfolio.models

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)