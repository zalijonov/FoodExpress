package uz.alijonov.foodexpress.model

import java.io.Serializable

data class CategoryModel(
    val id: Int,
    val title: String,
    val image: String
): Serializable
