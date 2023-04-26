package uz.alijonov.foodexpress.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import uz.alijonov.foodexpress.view.custom.ProgressDialogFragment

abstract class BaseFragment<B: ViewBinding> : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var binding: B
    var progressDialogFragment: ProgressDialogFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = getViewBinding()
        return binding.root
    }

    abstract fun getViewBinding(): B

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        loadData()
        setData()
    }

    open fun getTitle(): String{
        return tag ?: ""
    }

    override fun onRefresh() {
        loadData()
    }

    abstract fun setupViews()
    abstract fun loadData()
    abstract fun setData()


    fun setProgress(inProgress: Boolean){
        if (inProgress){
            progressDialogFragment = ProgressDialogFragment()
            progressDialogFragment?.isCancelable = false
            progressDialogFragment?.show(childFragmentManager, progressDialogFragment!!.tag)
        }else{
            progressDialogFragment?.dismiss()
        }
    }
}