package uz.alijonov.foodexpress.model.request

import java.io.Serializable

data class RegisterRequest(
    val fullname: String,
    val phone: String,
    val password: String
): Serializable
