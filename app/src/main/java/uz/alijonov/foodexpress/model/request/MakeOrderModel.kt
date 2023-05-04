package uz.alijonov.foodexpress.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MakeOrderModel(
    val order_type: OrderType,
    val adress: String,
    val latitude: Double,
    val longitude: Double,
    val order_products: List<OrderProductModel>
) : Serializable

data class OrderProductModel(
    val id: Int,
    val count: Int
): Serializable

enum class OrderType {
    @SerializedName("cash")
    CASH,
    @SerializedName("credit_card")
    CREDIT_CARD
}
