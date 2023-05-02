package uz.alijonov.foodexpress.model

data class EventModel<T>(
    val event: Int,
    val data: T
)
