package uz.alijonov.foodexpress.base

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewbinding.ViewBinding
import uz.alijonov.foodexpress.view.custom.ProgressDialogFragment

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {
    abstract fun getViewBinding(): B
    abstract fun initViews()
    abstract fun loadData()
    abstract fun initData()
    abstract fun updateData()
    lateinit var binding: B
    var progressDialogFragment: ProgressDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        initViews()
        loadData()
        initData()
    }

    fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it

        view?.clearFocus()
        view?.isSelected = false

        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


//    override fun attachBaseContext(newBase: Context?) {
//        super.attachBaseContext(LocaleManager.setLocale(newBase))
//    }

    fun setProgress(inProgress: Boolean) {
        if (inProgress) {
            progressDialogFragment = ProgressDialogFragment()
            progressDialogFragment?.isCancelable = false
            progressDialogFragment?.show(supportFragmentManager, progressDialogFragment!!.tag)
        } else {
            progressDialogFragment?.dismiss()
        }
    }

}