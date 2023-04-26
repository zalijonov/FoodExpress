package uz.alijonov.foodexpress.screen.main.cart

import uz.alijonov.foodexpress.base.BaseFragment
import uz.alijonov.foodexpress.databinding.FragmentCartBinding

class CartFragment : BaseFragment<FragmentCartBinding>() {
    companion object {

        @JvmStatic
        fun newInstance() =
            CartFragment()
    }

    override fun getViewBinding(): FragmentCartBinding {
        return FragmentCartBinding.inflate(layoutInflater)
    }

    override fun setupViews() {

    }

    override fun loadData() {

    }

    override fun setData() {

    }
}