package uz.alijonov.foodexpress.model.response

import java.io.Serializable

data class AuthResponse(
    val id: Int,
    val token: String,
    val fullname: String,
    val avatar: String?,
    val phone: String,
    val status: String
): Serializable
