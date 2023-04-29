package uz.alijonov.foodexpress.model.request

import java.io.Serializable

data class LoginRequest(
    val phone: String,
    val password: String
) : Serializable
