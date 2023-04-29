package uz.alijonov.foodexpress.screen.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.alijonov.foodexpress.model.CategoryModel
import uz.alijonov.foodexpress.model.OfferModel
import uz.alijonov.foodexpress.model.RestaurantModel
import uz.alijonov.foodexpress.model.request.MakeRatingRequest
import uz.alijonov.foodexpress.repository.UserRepository

class MainViewModel : ViewModel() {
    private val userRepository = UserRepository()

    val error = MutableLiveData<String>()
    val progress = MutableLiveData<Boolean>()
    private val tempProgress = MutableLiveData<Boolean>()
    val offerData = MutableLiveData<List<OfferModel>>()
    val categoryData = MutableLiveData<List<CategoryModel>>()
    val nearbyData = MutableLiveData<List<RestaurantModel>>()
    val topData = MutableLiveData<List<RestaurantModel>>()
    val restaurantDetailData = MutableLiveData<RestaurantModel>()
    val makeRatingData = MutableLiveData<String>()

    fun getOffers() {
        userRepository.getOffers(error, progress, offerData)
    }

    fun getCategories() {
        userRepository.getCategories(error, tempProgress, categoryData)
    }

    fun getNearbyRestaurants() {
        userRepository.getRestaurantNearby(error, tempProgress, nearbyData)
    }

    fun getTopRestaurant() {
        userRepository.getRestaurantTop(error, tempProgress, topData)
    }

    fun getRestaurantDetail(id: Int) {
        userRepository.getRestaurantDetail(id, error, progress, restaurantDetailData)
    }

    fun makeRating(request: MakeRatingRequest) {
        userRepository.makeRating(request, error, progress, makeRatingData)
    }


}