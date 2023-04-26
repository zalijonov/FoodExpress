package uz.alijonov.foodexpress

import android.app.Application
import com.orhanobut.hawk.Hawk
import uz.alijonov.foodexpress.api.Client
import uz.alijonov.foodexpress.utils.Constants

class MyApp : Application() {

    companion object {
        lateinit var app: Application
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        Hawk.init(this).build()
//        Client.initClient(this, Constants.HOST)
    }
}