package uz.alijonov.foodexpress.model

data class RegionModel(
    val id: Int,
    val name_uz: String,
    val created_at: String?,
    val districts: List<DistrictModel>
)

data class DistrictModel(
    val id: Int,
    val region_id: Int,
    val name_uz: String,
    val created_at: String?
)

