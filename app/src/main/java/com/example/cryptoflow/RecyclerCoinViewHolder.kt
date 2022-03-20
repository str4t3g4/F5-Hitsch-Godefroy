package com.example.cryptoflow

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// binding the views
class RecyclerCoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val itemCoin: TextView = itemView.findViewById(R.id.item_coin)
    val itemPrice : TextView = itemView.findViewById(R.id.item_price)
    val itemImage : ImageView = itemView.findViewById(R.id.item_image)

}