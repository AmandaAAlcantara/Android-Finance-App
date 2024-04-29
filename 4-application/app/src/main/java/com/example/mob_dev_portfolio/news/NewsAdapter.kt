package com.example.mob_dev_portfolio.news
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mob_dev_portfolio.R
import com.kwabenaberko.newsapilib.models.Article
import com.squareup.picasso.Picasso

class NewsAdapter(private val articleList: MutableList<Article>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_recycler_row, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articleList[position]
        holder.titleTextView.text = article.title
        holder.sourceTextView.text = article.source?.name
        Picasso.get().load(article.urlToImage)
            .error(R.drawable.baseline_image_not_supported_24)
            .placeholder(R.drawable.baseline_image_not_supported_24)
            .into(holder.imageView)
    }

    fun updateData(data: List<Article>) {
        articleList.clear()
        articleList.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.article_title)
        val sourceTextView: TextView = itemView.findViewById(R.id.article_source)
        val imageView: ImageView = itemView.findViewById(R.id.article_image_view)
    }
}
