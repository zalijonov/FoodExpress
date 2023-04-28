package uz.alijonov.foodexpress.repository

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import uz.alijonov.foodexpress.MyApp
import uz.alijonov.foodexpress.api.BaseResponse
import uz.alijonov.foodexpress.api.Client
import uz.alijonov.foodexpress.model.CategoryModel
import uz.alijonov.foodexpress.model.OfferModel
import uz.alijonov.foodexpress.model.RestaurantModel
import uz.alijonov.foodexpress.model.request.LoginRequest
import uz.alijonov.foodexpress.model.request.MakeRatingRequest
import uz.alijonov.foodexpress.model.request.RegisterRequest
import uz.alijonov.foodexpress.model.request.RestaurantRequest
import uz.alijonov.foodexpress.model.request.SortType
import uz.alijonov.foodexpress.model.response.AuthResponse
import uz.alijonov.foodexpress.utils.Constants

class UserRepository {

    private val api = Client.initClient(MyApp.app, Constants.HOST)
    private val compositeDisposable = CompositeDisposable()

    fun login(
        request: LoginRequest,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<AuthResponse>
    ) {
        compositeDisposable.add(api.login(request).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .doOnSubscribe { progress.value = true }
            .subscribeWith(object : DisposableObserver<BaseResponse<AuthResponse>>() {
                override fun onNext(t: BaseResponse<AuthResponse>) {
                    if (t.success) {
                        success.value = t.data
                    } else {
                        error.value = t.message
                    }
                }

                override fun onError(e: Throwable) {
                    error.value = e.message
                }

                override fun onComplete() {

                }
            })
        )
    }

    fun register(
        request: RegisterRequest,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<AuthResponse>
    ) {
        compositeDisposable.add(api.registration(request).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .doOnSubscribe { progress.value = true }
            .subscribeWith(object : DisposableObserver<BaseResponse<AuthResponse>>() {
                override fun onNext(t: BaseResponse<AuthResponse>) {
                    if (t.success) {
                        success.value = t.data
                    } else {
                        error.value = t.message
                    }
                }

                override fun onError(e: Throwable) {
                    error.value = e.message
                }

                override fun onComplete() {

                }
            })
        )
    }

    fun getOffers(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<OfferModel>>
    ) {
        compositeDisposable.add(api.getOffers().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .doOnSubscribe { progress.value = true }
            .subscribeWith(object : DisposableObserver<BaseResponse<List<OfferModel>>>() {
                override fun onNext(t: BaseResponse<List<OfferModel>>) {
                    if (t.success) {
                        success.value = t.data
                    } else {
                        error.value = t.message
                    }
                }

                override fun onError(e: Throwable) {
                    error.value = e.message
                }

                override fun onComplete() {

                }
            })
        )
    }

    fun getCategories(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<CategoryModel>>
    ) {
        compositeDisposable.add(api.getCategories().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .doOnSubscribe { progress.value = true }
            .subscribeWith(object : DisposableObserver<BaseResponse<List<CategoryModel>>>() {
                override fun onNext(t: BaseResponse<List<CategoryModel>>) {
                    if (t.success) {
                        success.value = t.data
                    } else {
                        error.value = t.message
                    }
                }

                override fun onError(e: Throwable) {
                    error.value = e.message
                }

                override fun onComplete() {

                }
            })
        )
    }

    fun getRestaurantNearby(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<RestaurantModel>>
    ) {
        compositeDisposable.add(api.getRestaurants(RestaurantRequest(sort = SortType.DISTANCE))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .doOnSubscribe { progress.value = true }
            .subscribeWith(object : DisposableObserver<BaseResponse<List<RestaurantModel>>>() {
                override fun onNext(t: BaseResponse<List<RestaurantModel>>) {
                    if (t.success) {
                        success.value = t.data
                    } else {
                        error.value = t.message
                    }
                }

                override fun onError(e: Throwable) {
                    error.value = e.message
                }

                override fun onComplete() {

                }
            })
        )
    }

    fun getRestaurantTop(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<RestaurantModel>>
    ) {
        compositeDisposable.add(api.getRestaurants(RestaurantRequest(sort = SortType.RATING))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .doOnSubscribe { progress.value = true }
            .subscribeWith(object : DisposableObserver<BaseResponse<List<RestaurantModel>>>() {
                override fun onNext(t: BaseResponse<List<RestaurantModel>>) {
                    if (t.success) {
                        success.value = t.data
                    } else {
                        error.value = t.message
                    }
                }

                override fun onError(e: Throwable) {
                    error.value = e.message
                }

                override fun onComplete() {

                }
            })
        )
    }

    fun getRestaurantDetail(
        id: Int,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<RestaurantModel>
    ) {
        compositeDisposable.add(api.getRestaurantDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .doOnSubscribe { progress.value = true }
            .subscribeWith(object : DisposableObserver<BaseResponse<RestaurantModel>>() {
                override fun onNext(t: BaseResponse<RestaurantModel>) {
                    if (t.success) {
                        success.value = t.data
                    } else {
                        error.value = t.message
                    }
                }

                override fun onError(e: Throwable) {
                    error.value = e.message
                }

                override fun onComplete() {

                }
            })
        )
    }


    fun makeRating(
        request: MakeRatingRequest,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<Any?>
    ) {
        compositeDisposable.add(api.makeRating(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .doOnSubscribe { progress.value = true }
            .subscribeWith(object : DisposableObserver<BaseResponse<Any?>>() {
                override fun onNext(t: BaseResponse<Any?>) {
                    if (t.success) {
                        success.value = t.data
                    } else {
                        error.value = t.message
                    }
                }

                override fun onError(e: Throwable) {
                    error.value = e.message
                }

                override fun onComplete() {

                }
            })
        )
    }


}