package com.example.cryptoflow.placeholder

import kotlinx.serialization.Serializable

@Serializable
data class Coin (
    val name: String,
    val current_price:Double,
    val image: String? = null
    ){
    override fun toString(): String {
        return name;
    }
}
