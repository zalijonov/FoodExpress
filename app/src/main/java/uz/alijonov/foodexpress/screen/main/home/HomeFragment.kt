package uz.alijonov.foodexpress.screen.main.home

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.alijonov.foodexpress.base.BaseAdapterListener
import uz.alijonov.foodexpress.base.BaseFragment
import uz.alijonov.foodexpress.databinding.FragmentHomeBinding
import uz.alijonov.foodexpress.model.RestaurantModel
import uz.alijonov.foodexpress.screen.main.MainViewModel
import uz.alijonov.foodexpress.screen.main.restaurant.RestaurantDetailActivity
import uz.alijonov.foodexpress.utils.Constants
import uz.alijonov.foodexpress.view.adapter.CategoryAdapter
import uz.alijonov.foodexpress.view.adapter.NearbyRestaurantAdapter
import uz.alijonov.foodexpress.view.adapter.OfferAdapter
import uz.alijonov.foodexpress.view.adapter.RestaurantAdapterListener
import uz.alijonov.foodexpress.view.adapter.TopRestaurantAdapter
import uz.alijonov.foodexpress.base.showError
import uz.alijonov.foodexpress.base.startActivity


class HomeFragment : BaseFragment<FragmentHomeBinding>(), RestaurantAdapterListener {

    companion object {

        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }

    lateinit var viewModel: MainViewModel
    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun setupViews() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.error.observe(this) {
            activity?.showError(it)
        }

        viewModel.progress.observe(this) {
            setProgress(it)
        }

        viewModel.offerData.observe(this) {
            binding.recOffer.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            binding.recOffer.adapter = OfferAdapter(it, object : BaseAdapterListener {
                override fun onClickItem(item: Any?) {

                }
            })
        }

        viewModel.categoryData.observe(this) {
            binding.recCategory.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            binding.recCategory.adapter = CategoryAdapter(it, object : BaseAdapterListener {
                override fun onClickItem(item: Any?) {

                }
            })
        }

        viewModel.nearbyData.observe(this) {
            binding.recNearby.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            binding.recNearby.adapter = NearbyRestaurantAdapter(it, this)
        }

        viewModel.topData.observe(this) {
            binding.recTop.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            binding.recTop.adapter = TopRestaurantAdapter(it, this)
        }
    }

    override fun loadData() {
        viewModel.getOffers()
        viewModel.getCategories()
        viewModel.getNearbyRestaurants()
        viewModel.getTopRestaurant()
    }

    override fun setData() {

    }

    override fun onClick(item: RestaurantModel) {

        activity?.startActivity<RestaurantDetailActivity>(Constants.EXTRA_DATA, item.id)
    }

    override fun onClickItem(item: Any?) {

    }
}