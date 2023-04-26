package uz.alijonov.foodexpress.model

import java.io.Serializable

data class OfferModel(
    val id: Int,
    val title: String,
    val image: String
) : Serializable
