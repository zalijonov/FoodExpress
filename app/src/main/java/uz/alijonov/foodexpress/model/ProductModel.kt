package uz.alijonov.foodexpress.model

import java.io.Serializable

data class ProductModel(
    val id: Int,
    val restaurant_id: Int,
    val food_id: Int,
    val name: String,
    val image: String,
    val price: Double,
    val ingredients: List<String>,
    val created_at: String,
    val updated_at: String,
    var cart_count: Int = 0
): Serializable
