package com.example.cryptoflow

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

//binding the views
class RecyclerNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val newsSite: TextView = itemView.findViewById(R.id.news_site)
    val newsTitle : TextView = itemView.findViewById(R.id.news_title)
    val newsImage : ImageView = itemView.findViewById(R.id.news_image)
    val newsMinute : TextView = itemView.findViewById(R.id.news_mins)
    val newsLayout : CardView = itemView.findViewById(R.id.cardView_news)
}