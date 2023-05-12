package uz.alijonov.foodexpress.model.request

import com.google.gson.annotations.SerializedName
import uz.alijonov.foodexpress.utils.Constants

data class RestaurantRequest(
    var region_id: Int = 0,
    var district_id: Int = 0,
    var category_id: Int = 0,
    var food_id: Int = 0,
    var keyword: String = "",
    var sort: SortType = SortType.DISTANCE,
    var longitude: Double = Constants.LONGITUDE,
    val latitude: Double = Constants.LATITUDE
)

enum class SortType {
    @SerializedName("distance")
    DISTANCE,

    @SerializedName("rating")
    RATING
}
