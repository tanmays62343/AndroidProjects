package com.trx.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import com.trx.databinding.RecyclerNewsItemBinding
import com.trx.models.Article

class NewsAdapter(val context: Context, private val articles: List<Article>) :
    Adapter<NewsAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {

        val binding = RecyclerNewsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        val article = articles[position]
        holder.bind(article)
        holder.bindImages(article)

    }

    override fun getItemCount(): Int {
        Log.d("BRB", "getItemCount : ${articles.size}")
        return articles.size
    }

    class ArticleViewHolder(private val binding: RecyclerNewsItemBinding) :
        ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.model = article
            binding.executePendingBindings()
        }

        fun bindImages(article: Article){
            Picasso.get()
                .load(article.urlToImage)
                .into(binding.newsImage)
        }

    }

}