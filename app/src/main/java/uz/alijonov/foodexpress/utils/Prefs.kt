package uz.alijonov.foodexpress.utils

import com.orhanobut.hawk.Hawk
import uz.alijonov.foodexpress.model.ProductModel
import uz.alijonov.foodexpress.model.response.AuthResponse

object Prefs {

    private const val PREF_TOKEN = "pref_token"
    private const val PREF_USER = "pref_user"
    private const val PREF_CART = "pref_cart"

    fun setToken(value: String) {
        Hawk.put(PREF_TOKEN, value)
    }

    fun getToken(): String {
        return Hawk.get(PREF_TOKEN, "")
    }

    fun setUser(value: AuthResponse) {
        Hawk.put(PREF_USER, value)
    }

    fun getUser(): AuthResponse {
        return Hawk.get(PREF_USER)
    }

    fun add2Cart(item: ProductModel) {
        val list = getCartList().toMutableList()
        if (item.cart_count == 0) {
            var index = 0
            var removeIndex = -1
            list.forEach {
                if (it.id == item.id) {
                    removeIndex = index
                }
                index++
            }
            if (removeIndex > -1) {
                list.removeAt(removeIndex)
            }
        } else {
            var isHave = false
            list.forEach {
                if (it.id == item.id) {
                    it.cart_count = item.cart_count
                    isHave = true
                }
            }

            if (!isHave) {
                list.add(0, item)
            }
        }

        Hawk.put(PREF_CART, list)
    }

    fun getItemCartCount(item: ProductModel): Int{
        val list = getCartList().toMutableList()
        val cart = list.firstOrNull { it.id == item.id }
        return cart?.cart_count ?: 0
    }

    fun getCartList(): List<ProductModel> {
        return Hawk.get(PREF_CART, emptyList())
    }
}