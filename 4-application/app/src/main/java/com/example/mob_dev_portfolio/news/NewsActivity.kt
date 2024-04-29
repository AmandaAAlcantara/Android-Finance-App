package com.example.mob_dev_portfolio.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mob_dev_portfolio.databinding.ActivityNewsBinding
import com.example.mob_dev_portfolio.news.NewsAdapter
import com.google.android.material.progressindicator.LinearProgressIndicator

//code functionality inspired by Easy Tuto: available at:  https://www.youtube.com/watch?v=oxy30aSlafs&t=13s

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressIndicator: LinearProgressIndicator
    private lateinit var adapter: NewsAdapter
    private var articleList = mutableListOf<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.newsRecyclerView
        progressIndicator = binding.progressBar

        setupRecyclerView()

        getNews()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NewsAdapter(articleList)
        recyclerView.adapter = adapter
    }

    private fun changeInProgress(show: Boolean) {
        progressIndicator.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    private fun getNews() {
        changeInProgress(true)
        val newsApiClient = NewsApiClient("7cb840a871a14792b0a010003db48f6a")
        newsApiClient.getTopHeadlines(
            TopHeadlinesRequest.Builder()
                .language("en")
                .category("business")
                .build(),
            object : NewsApiClient.ArticlesResponseCallback {
                override fun onSuccess(response: ArticleResponse) {
                    runOnUiThread {
                        changeInProgress(false)
                        articleList.clear()
                        articleList.addAll(response.articles)
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(throwable: Throwable) {
                    Log.i("GOT FAILURE", throwable.message ?: "Unknown error")
                }
            }
        )
    }
}
