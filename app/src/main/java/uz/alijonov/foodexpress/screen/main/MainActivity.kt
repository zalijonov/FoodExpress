package uz.alijonov.foodexpress.screen.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import uz.alijonov.foodexpress.R
import uz.alijonov.foodexpress.base.BaseActivity
import uz.alijonov.foodexpress.databinding.ActivityMainBinding
import uz.alijonov.foodexpress.model.EventModel
import uz.alijonov.foodexpress.screen.main.cart.CartFragment
import uz.alijonov.foodexpress.screen.main.home.HomeFragment
import uz.alijonov.foodexpress.screen.main.map.MapsFragment
import uz.alijonov.foodexpress.screen.main.profile.ProfileFragment
import uz.alijonov.foodexpress.utils.Constants

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val homeFragment = HomeFragment.newInstance()
    val mapFragment = MapsFragment()
    val cartFragment = CartFragment.newInstance()
    val profileFragment = ProfileFragment.newInstance()
    var activeFragment: Fragment = homeFragment

    private var currentLocation: Location? = null
    lateinit var locationManager: LocationManager

    //    private lateinit var fusedLocationServie: FusedLocationProviderClient*
    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initViews() {

        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }

        if(intent.hasExtra(Constants.START_FRAGMENT)){
            if(intent.getIntExtra(Constants.START_FRAGMENT, 0) == R.id.actionCart){
                binding.bottomNavigation.selectedItemId = R.id.actionCart

                activeFragment = cartFragment
            }
        }

//        isLocationPermissionGranted()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if(isLocationPermissionGranted()) {
            getLocation()
        }

        supportFragmentManager.beginTransaction().add(R.id.flContainer, activeFragment)
            .show(activeFragment).commit()

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.actionHome -> {
                    supportFragmentManager.beginTransaction().detach(activeFragment)
                        .remove(activeFragment).add(R.id.flContainer, homeFragment)
                        .show(homeFragment).commit()

                    activeFragment = homeFragment
                }

                R.id.actionMap -> {
                    supportFragmentManager.beginTransaction().detach(activeFragment)
                        .remove(activeFragment).add(R.id.flContainer, mapFragment).show(mapFragment)
                        .commit()

                    activeFragment = mapFragment
                }

                R.id.actionCart -> {
                    supportFragmentManager.beginTransaction().detach(activeFragment)
                        .remove(activeFragment).add(R.id.flContainer, cartFragment)
                        .show(cartFragment).commit()

                    activeFragment = cartFragment
                }

                R.id.actionProfile -> {
                    supportFragmentManager.beginTransaction().detach(activeFragment)
                        .remove(activeFragment).add(R.id.flContainer, profileFragment)
                        .show(profileFragment).commit()

                    activeFragment = profileFragment
                }
            }

            return@setOnItemSelectedListener true
        }

        binding.bottomNavigation.setOnItemReselectedListener {

        }

    }

    @Subscribe
    fun onEvent(event: EventModel<Any>){

    }

    override fun onDestroy() {
        super.onDestroy()
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    override fun loadData() {

    }

    override fun initData() {

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