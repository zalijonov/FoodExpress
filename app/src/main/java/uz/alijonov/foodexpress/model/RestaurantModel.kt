package uz.alijonov.foodexpress.model

import java.io.Serializable

data class RestaurantModel(
    val id: Int,
    val region_id: Int,
    val district_id: Int,
    val name: String,
    val phone: String,
    val address: String,
    val comment: String,
    val main_image: String,
    val latitude: Double,
    val longitude: Double,
    val status: String,
    val created_at: String,
    val updated_at: String,
    val distance: Double,
    val rating: Double,
    val rating_count: Int,
    val district: MiniDistrict,
    val region: MiniRegion,
    val product_list: List<ProductModel>
):Serializable

data class MiniDistrict(
    val id: Int,
    val region_id: Int,
    val district_name: String
):Serializable

data class MiniRegion(
    val id: Int,
    val region_name: String
):Serializable
