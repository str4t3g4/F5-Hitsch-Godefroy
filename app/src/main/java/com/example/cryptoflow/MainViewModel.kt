package com.example.cryptoflow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoflow.placeholder.Coin
import com.example.cryptoflow.placeholder.News
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel: ViewModel() {
    var coin : MutableLiveData<List<Coin>> = MutableLiveData(listOf())
    var news : MutableLiveData<News> = MutableLiveData(News(mutableListOf()))

    fun cryptoAPI() {
        val options = "order=market_cap_desc&per_page=10&page=1&sparkline=false"
        val currency = "vs_currency=eur"
        // Create a new coroutine to call API
        viewModelScope.launch(Dispatchers.IO) {
            val client = HttpClient(CIO){
                install(JsonFeature){
                    serializer = GsonSerializer {
                        setPrettyPrinting()
                        disableHtmlEscaping()
                    }
                }
            }
            try {
                coin.postValue(client.get("https://api.coingecko.com/api/v3/coins/markets?$currency&$options"))
            }
            catch (error : Exception){
                println(error.printStackTrace())
            }

        }
    }

    fun newsAPI(){
        val apiKey = "6fa121bec8544fd8bc3632a1f53f25dc"
        val languages ="fr,en"
        viewModelScope.launch(Dispatchers.IO) {
            val client = HttpClient(CIO){
                install(JsonFeature){
                    serializer = GsonSerializer{
                        setPrettyPrinting()
                        disableHtmlEscaping()
                    }
                }
            }
            try {
                news.postValue(client.get("https://newsapi.org/v2/everything?q=crypto&pageSize=20&sortBy=publishedAt&languages=$languages&apiKey=$apiKey"))
            }
            catch (error : Exception){
                println(error.printStackTrace())
            }
        }
    }




}