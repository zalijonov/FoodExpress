package uz.alijonov.foodexpress.api

import java.io.Serializable

data class BaseResponse<T>(
    val success: Boolean,
    val data: T,
    val message: String,
    val error_code: Int
): Serializable
