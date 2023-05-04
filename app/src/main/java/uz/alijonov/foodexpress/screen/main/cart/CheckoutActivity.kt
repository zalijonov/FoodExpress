package uz.alijonov.foodexpress.screen.main.cart

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import uz.alijonov.foodexpress.R
import uz.alijonov.foodexpress.base.BaseActivity
import uz.alijonov.foodexpress.databinding.ActivityCheckoutBinding
import uz.alijonov.foodexpress.model.request.MakeOrderModel
import uz.alijonov.foodexpress.model.request.OrderProductModel
import uz.alijonov.foodexpress.model.request.OrderType
import uz.alijonov.foodexpress.screen.main.MainViewModel
import uz.alijonov.foodexpress.utils.Constants
import uz.alijonov.foodexpress.utils.Prefs
import uz.alijonov.foodexpress.view.custom.JWAlertDialogListener
import uz.alijonov.foodexpress.base.formattedAmount
import uz.alijonov.foodexpress.base.showError
import uz.alijonov.foodexpress.base.showSuccess
import uz.alijonov.foodexpress.base.showWarning

class CheckoutActivity : BaseActivity<ActivityCheckoutBinding>() {

    var cashChecked = true
    var orderType = OrderType.CASH

    private var currentLocation: Location? = null
    lateinit var locationManager: LocationManager
    lateinit var viewModel: MainViewModel
    override fun getViewBinding(): ActivityCheckoutBinding {
        return ActivityCheckoutBinding.inflate(layoutInflater)
    }

    override fun initViews() {

        isLocationPermissionGranted()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        binding.imgGetLocation.setOnClickListener { getLocation() }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.error.observe(this) {
            showError(it)
        }

        viewModel.progress.observe(this) {
            setProgress(it)
        }

        viewModel.makeOrderData.observe(this) {
            showSuccess(it, object : JWAlertDialogListener {
                override fun onClickDismiss() {
                    Prefs.clearCart()
                    finish()
                }

            })
        }

        binding.lyCard.setOnClickListener {
            cashChecked = false
            initData()
        }

        binding.lyPayment.setOnClickListener {
            cashChecked = true
            initData()
        }

        var totalAmount = 0.0
        Prefs.getCartList().forEach {
            totalAmount += it.price * it.cart_count
        }

        binding.tvTotalPrice.text = "${totalAmount.formattedAmount()} so'm"
        binding.btnOrder.setOnClickListener {
            if (binding.edLocation.text.isNullOrEmpty()) {
                showWarning("Manzilni kiriting!")
            } else {
                val list = arrayListOf<OrderProductModel>()
                Prefs.getCartList().forEach {
                    list.add(OrderProductModel(it.id, it.cart_count))
                }
                val makeOrder = MakeOrderModel(
                    orderType,
                    binding.edLocation.text.toString(),
                    currentLocation?.latitude ?: 0.0,
                    currentLocation?.longitude ?: 0.0,
                    list
                )

                viewModel.makeOrder(makeOrder)
            }
        }
    }

    override fun loadData() {

    }

    override fun initData() {
        if (cashChecked) {
            orderType = OrderType.CASH
            binding.lyPayment.background =
                ContextCompat.getDrawable(this, R.drawable.payment_backgr_selected)
            binding.imgPayments.imageTintList =
                ContextCompat.getColorStateList(this, R.color.yellow)
            binding.tvCash.setTextColor(ContextCompat.getColor(this, R.color.yellow))

            binding.lyCard.background = ContextCompat.getDrawable(this, R.drawable.payment_backgr)
            binding.imgCreditCard.imageTintList =
                ContextCompat.getColorStateList(this, R.color.tint_49)
            binding.tvCreditCard.setTextColor(ContextCompat.getColor(this, R.color.white))
        } else {
            orderType = OrderType.CREDIT_CARD
            binding.lyCard.background =
                ContextCompat.getDrawable(this, R.drawable.payment_backgr_selected)
            binding.imgCreditCard.imageTintList =
                ContextCompat.getColorStateList(this, R.color.yellow)
            binding.tvCreditCard.setTextColor(ContextCompat.getColor(this, R.color.yellow))

            binding.lyPayment.background =
                ContextCompat.getDrawable(this, R.drawable.payment_backgr)
            binding.imgPayments.imageTintList =
                ContextCompat.getColorStateList(this, R.color.tint_49)
            binding.tvCash.setTextColor(ContextCompat.getColor(this, R.color.white))
        }
    }

    override fun updateData() {

    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        val hasGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        var locationByGps: Location? = null
        val gpsLocationListener = LocationListener { location ->
            locationByGps = location
        }

        var locationByNetwork: Location? = null
        val networkLocationListener = LocationListener { location ->
            locationByNetwork = location
        }

        if (hasGPS) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                0F,
                gpsLocationListener
            )
        }

        if (hasNetwork) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,
                0F,
                networkLocationListener
            )
        }

        val lastKnownLocationByGps =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        lastKnownLocationByGps?.let { locationByGps = lastKnownLocationByGps }

        val lastKnownLocationByNetwork =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        lastKnownLocationByNetwork?.let { locationByNetwork = lastKnownLocationByNetwork }

        if (locationByGps != null && locationByNetwork != null) {
            if (locationByGps!!.accuracy > locationByNetwork!!.accuracy) {
                currentLocation = locationByGps
                Constants.LATITUDE = currentLocation?.latitude ?: 0.0
                Constants.LONGITUDE = currentLocation?.longitude ?: 0.0
            } else {
                currentLocation = locationByNetwork
                Constants.LATITUDE = currentLocation?.latitude ?: 0.0
                Constants.LONGITUDE = currentLocation?.longitude ?: 0.0
            }
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 100
            )
            false
        } else {
            true
        }
    }
}