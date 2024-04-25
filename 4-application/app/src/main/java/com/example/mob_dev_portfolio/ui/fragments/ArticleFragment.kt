package com.example.mob_dev_portfolio.ui.fragments

import com.example.mob_dev_portfolio.ui.NewsActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mob_dev_portfolio.R
import com.example.mob_dev_portfolio.ui.NewsViewModel

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
    }
}

