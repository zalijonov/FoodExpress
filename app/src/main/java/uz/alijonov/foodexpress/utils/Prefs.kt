package uz.alijonov.foodexpress.utils

import com.orhanobut.hawk.Hawk
import uz.alijonov.foodexpress.model.response.AuthResponse

object Prefs {

    private const val PREF_TOKEN = "pref_token"
    private const val PREF_USER = "pref_user"

    fun setToken(value: String){
        Hawk.put(PREF_TOKEN, value)
    }

    fun getToken(): String{
        return Hawk.get(PREF_TOKEN, "")
    }

    fun setUser(value: AuthResponse){
        Hawk.put(PREF_USER, value)
    }

    fun getUser(): AuthResponse{
        return Hawk.get(PREF_USER)
    }
}