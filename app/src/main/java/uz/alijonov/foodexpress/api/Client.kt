package uz.alijonov.foodexpress.api

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.alijonov.foodexpress.utils.Constants
import uz.alijonov.foodexpress.utils.Prefs
import java.util.concurrent.TimeUnit

object Client {

    var retrofit: Retrofit? = null

    fun initClient(context: Context, host: String): Api {
        val gson = GsonBuilder().setLenient().create()

        retrofit = Retrofit.Builder()
            .baseUrl(host)
            .client(getOkHttpClient(context))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit!!.create(Api::class.java)
    }

    fun getOkHttpClient(context: Context): OkHttpClient {
        var builder = OkHttpClient().newBuilder()
        builder.retryOnConnectionFailure(false)
        builder.connectTimeout(60, TimeUnit.SECONDS)
        builder.writeTimeout(60, TimeUnit.SECONDS)
        builder.readTimeout(60, TimeUnit.SECONDS)
//        if (BuildConfig.DEBUG) {
            builder.addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(true)
                    .build()
            )
//        }

        builder.addInterceptor(AppInterceptor())
        return builder.build()
    }

    class AppInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            return chain.proceed(getRequest(original))
        }


        fun getRequest(original: Request): Request {
            var builder = original.newBuilder()
            builder.addHeader("Key", Constants.DEVELOPER_KEY)
            if (!Prefs.getToken().isNullOrEmpty()) {
                builder.addHeader("Token", Prefs.getToken().toString())
            }

            builder.method(original.method(), original.body())
            return builder.build()
        }
    }

}