package uz.alijonov.foodexpress.screen.main.restaurant

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.alijonov.foodexpress.R
import uz.alijonov.foodexpress.base.BaseActivity
import uz.alijonov.foodexpress.base.BaseAdapterListener
import uz.alijonov.foodexpress.databinding.ActivityRestaurantDetailBinding
import uz.alijonov.foodexpress.databinding.MakeRatingLayoutBinding
import uz.alijonov.foodexpress.model.RestaurantModel
import uz.alijonov.foodexpress.model.request.MakeRatingRequest
import uz.alijonov.foodexpress.screen.main.MainActivity
import uz.alijonov.foodexpress.screen.main.MainViewModel
import uz.alijonov.foodexpress.utils.Constants
import uz.alijonov.foodexpress.view.adapter.FoodAdapter
import uz.alijonov.foodexpress.base.loadImage
import uz.alijonov.foodexpress.base.showError
import uz.alijonov.foodexpress.base.showSuccess
import uz.alijonov.foodexpress.base.showWarning
import uz.alijonov.foodexpress.base.startActivity
import java.text.DecimalFormat

class RestaurantDetailActivity : BaseActivity<ActivityRestaurantDetailBinding>() {

    private lateinit var viewModel: MainViewModel
    private var restaurant: RestaurantModel? = null
    private var resId = 0
    override fun getViewBinding(): ActivityRestaurantDetailBinding {
        return ActivityRestaurantDetailBinding.inflate(layoutInflater)
    }

    override fun initViews() {

        if (intent.hasExtra(Constants.EXTRA_DATA)) {
            resId = intent.getIntExtra(Constants.EXTRA_DATA, 0)
        }
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.error.observe(this) { showError(it) }
        viewModel.progress.observe(this) { setProgress(it) }
        viewModel.restaurantDetailData.observe(this) {
            restaurant = it
            binding.imageRes.loadImage(it.main_image)
            binding.ratingBar.rating = it.rating.toFloat()
            binding.tvRating.text = DecimalFormat("#.#").format(it.rating)
            binding.tvTitle.text = it.name
            binding.tvAddress.text = it.address
            binding.tvPhone.text = it.phone
            binding.tvDistance.text = it.distance.toString()
            binding.recFoods.layoutManager = GridLayoutManager(this, 2)
            binding.recFoods.adapter = FoodAdapter(it.product_list, object : BaseAdapterListener {
                override fun onClickItem(item: Any?) {}
            })
        }
        viewModel.makeRatingData.observe(this) { showSuccess(it) }
        binding.btnLeaveReview.setOnClickListener { showMakeRatingDialog() }

        binding.tvOpenMap.setOnClickListener {
            var geoUri = "http://maps.google.com/maps?q=loc:" + (restaurant?.latitude
                ?: 0.0) + "," + (restaurant?.longitude ?: 0.0) + " (" + restaurant?.name + ")"

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
            startActivity(intent)
        }

        binding.btnMakeOrder.setOnClickListener {
            startActivity<MainActivity>(Constants.START_FRAGMENT, R.id.actionCart)
        }

    }

    override fun loadData() {
        viewModel.getRestaurantDetail(resId)
    }

    override fun initData() {

    }

    override fun updateData() {

    }

    private fun showMakeRatingDialog() {
        val dialog = BottomSheetDialog(this, BottomSheetDialogFragment.STYLE_NORMAL)
        val view = MakeRatingLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(view.root)
        dialog.setCancelable(false)
        dialog.show()

        view.btnMakeRating.setOnClickListener {
            if (view.edComment.text.isNullOrEmpty() || view.ratingBar.rating == 0f) {
                showWarning("Iltimos hamma maydonlarni to'ldiring")
            } else {
                val review =
                    MakeRatingRequest(resId, view.ratingBar.rating, view.edComment.text.toString())

                viewModel.makeRating(review)
                dialog.dismiss()
            }
        }

        view.imgClose.setOnClickListener { dialog.dismiss() }
    }

}