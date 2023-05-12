package uz.alijonov.foodexpress.model.request

data class UpdatePasswordRequest(
    val old_password: String,
    val new_password: String,
    val sms_code: String
)
