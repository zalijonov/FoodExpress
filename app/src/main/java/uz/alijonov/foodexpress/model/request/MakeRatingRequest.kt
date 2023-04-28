package uz.alijonov.foodexpress.model.request

data class MakeRatingRequest(
    val restaurant_id: Int,
    val rating: Float,
    val comment: String
)
