package com.example.cryptoflow


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cryptoflow.placeholder.Coin
// Adapter for Coin
class CoinListAdapter(private val coins: MutableList<Coin>) : RecyclerView.Adapter<RecyclerCoinViewHolder>() {
    private val currency : String = " €"

    override fun getItemViewType(position: Int): Int {
        return R.layout.recyclerview_coin_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerCoinViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return RecyclerCoinViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerCoinViewHolder, position: Int) {
        holder.itemCoin.text = coins[position].name
        holder.itemPrice.text = coins[position].current_price.toString() + currency
        holder.itemImage.load(coins[position].image)
    }

    override fun getItemCount(): Int {
        return coins.size
    }

}