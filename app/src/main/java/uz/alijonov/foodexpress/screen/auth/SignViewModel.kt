package uz.alijonov.foodexpress.screen.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.alijonov.foodexpress.model.OfferModel
import uz.alijonov.foodexpress.model.request.LoginRequest
import uz.alijonov.foodexpress.model.request.RegisterRequest
import uz.alijonov.foodexpress.model.response.AuthResponse
import uz.alijonov.foodexpress.repository.UserRepository

class SignViewModel : ViewModel() {

    val userRepository = UserRepository()

    val error = MutableLiveData<String>()
    val progress = MutableLiveData<Boolean>()
    val authData = MutableLiveData<AuthResponse>()
    val offerData = MutableLiveData<List<OfferModel>>()

    fun login(request: LoginRequest) {
        userRepository.login(request, error, progress, authData)
    }

    fun register(request: RegisterRequest) {
        userRepository.register(request, error, progress, authData)
    }

}