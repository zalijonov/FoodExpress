package uz.alijonov.foodexpress.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetFragment<B: ViewBinding> : BottomSheetDialogFragment() {

    lateinit var binding: B

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

    abstract fun setupViews()
    abstract fun loadData()
    abstract fun setData()
}