package com.example.mob_dev_portfolio.repository

import com.example.mob_dev_portfolio.api.RetrofitInstance
import com.example.mob_dev_portfolio.db.ArticleDatabase


class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, category: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, category, pageNumber)
}