package uz.alijonov.foodexpress.utils

class Constants{
    companion object{

        //client    agent   local_trade     taxi_driver     taxi_client     taxi_operator
        const val APP_TYPE = "client"
        const val HOST = "http://openapi-foodexpress.devapp.uz/api/"
        const val HOST_IMAGE = "http://openapi-foodexpress.devapp.uz/"
        const val DEVELOPER_KEY = "xUA8m4ARCoeYGBYCmKvEcJJ4CsiONc"

        var LONGITUDE: Double = 0.0
        var LATITUDE: Double = 0.0

        const val START_FRAGMENT = "start_fragment"

        const val EXTRA_DATA = "extra_data"
        const val EXTRA_DATA_2 = "extra_data_2"

        const val EVENT_LOGOUT = 1
    }
}