package com.example.cryptoflow


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cryptoflow.placeholder.News
import java.time.Duration
import java.time.OffsetDateTime
import java.time.ZoneOffset


class NewsListAdapter(private val news: News, private val accessWeb: AccessWeb) : RecyclerView.Adapter<RecyclerNewsViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return R.layout.recyclerview_news_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerNewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        return RecyclerNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerNewsViewHolder, position: Int) {
        // get the useful information from the api call result
        val date :OffsetDateTime = OffsetDateTime.now( ZoneOffset.UTC )
        holder.newsSite.text = news.articles[position].source?.name
        holder.newsTitle.text = news.articles[position].title
        holder.newsImage.load(news.articles[position].urlToImage)
        println(news.articles[position].urlToImage)
        val minutes : String = Duration.between(OffsetDateTime.parse(news.articles[position].publishedAt),date).toMinutes().toString()  + " mins" // compute the time delay after publishing
        holder.newsMinute.text = minutes
        holder.newsLayout.setOnClickListener{
            accessWeb.onClick(news.articles[position].url.toString())
        }
    }

    override fun getItemCount(): Int {
        return news.articles.size
    }
}