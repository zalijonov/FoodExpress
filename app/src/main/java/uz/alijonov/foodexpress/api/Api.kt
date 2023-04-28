package uz.alijonov.foodexpress.api

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import uz.alijonov.foodexpress.model.CategoryModel
import uz.alijonov.foodexpress.model.OfferModel
import uz.alijonov.foodexpress.model.ProductModel
import uz.alijonov.foodexpress.model.RestaurantModel
import uz.alijonov.foodexpress.model.request.LoginRequest
import uz.alijonov.foodexpress.model.request.MakeRatingRequest
import uz.alijonov.foodexpress.model.request.RegisterRequest
import uz.alijonov.foodexpress.model.request.RestaurantRequest
import uz.alijonov.foodexpress.model.response.AuthResponse

interface Api {

    @POST("login")
    fun login(@Body request: LoginRequest): Observable<BaseResponse<AuthResponse>>

    @POST("registration")
    fun registration(@Body request: RegisterRequest): Observable<BaseResponse<AuthResponse>>

    @GET("offers")
    fun getOffers(): Observable<BaseResponse<List<OfferModel>>>

    @GET("categories")
    fun getCategories(): Observable<BaseResponse<List<CategoryModel>>>

    @POST("restaurants")
    fun getRestaurants(@Body request: RestaurantRequest): Observable<BaseResponse<List<RestaurantModel>>>

    @GET("restaurant_detail/{restaurant_id}")
    fun getRestaurantDetail(@Path("restaurant_id") id: Int): Observable<BaseResponse<RestaurantModel>>

    @GET("restaurant/{restaurant_id}/foods")
    fun getRestaurantFoods(@Path("restaurant_id") id: Int): Observable<BaseResponse<List<ProductModel>>>

    @POST("make_rating")
    fun makeRating(@Body request: MakeRatingRequest): Observable<BaseResponse<Any?>>

//    @GET("offer/{offer_id}/content")
//    fun getOffer(@Path("offer_id") id: Int):
}