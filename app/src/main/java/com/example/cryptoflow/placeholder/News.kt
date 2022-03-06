package com.example.cryptoflow.placeholder;

import kotlinx.serialization.Serializable

@Serializable
data class News (
        val articles : MutableList<Article>
        )

data class Article (
        val source : Source ?,
        val title : String ?,
        val description : String ?,
        val url : String ?,
        val urlToImage : String ?,
        val publishedAt : String ?
        )

data class Source (
        val name : String ?
        )