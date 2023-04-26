package uz.alijonov.foodexpress.screen.main.profile

import uz.alijonov.foodexpress.base.BaseFragment
import uz.alijonov.foodexpress.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {


    override fun getViewBinding(): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(layoutInflater)
    }

    override fun setupViews() {

    }

    override fun loadData() {

    }

    override fun setData() {

    }

    companion object {

        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}