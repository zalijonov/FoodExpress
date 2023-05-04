package uz.alijonov.foodexpress.screen.splash

import uz.alijonov.foodexpress.databinding.ActivitySplashBinding
import uz.alijonov.foodexpress.base.BaseActivity
import uz.alijonov.foodexpress.screen.auth.SignActivity
import uz.alijonov.foodexpress.screen.main.MainActivity
import uz.alijonov.foodexpress.utils.Prefs
import uz.alijonov.foodexpress.base.startClearTopActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun getViewBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        binding.imgLogo.postDelayed({
            if(Prefs.getToken().isEmpty()){
                startClearTopActivity<SignActivity>()
            } else {
                startClearTopActivity<MainActivity>()
            }
            finish()
        }, 2000)
    }

    override fun loadData() {

    }

    override fun initData() {

    }

    override fun updateData() {

    }

}