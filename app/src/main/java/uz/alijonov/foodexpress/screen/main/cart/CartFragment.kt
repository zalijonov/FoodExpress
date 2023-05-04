package uz.alijonov.foodexpress.screen.main.cart

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import uz.alijonov.foodexpress.base.BaseAdapterListener
import uz.alijonov.foodexpress.base.BaseFragment
import uz.alijonov.foodexpress.databinding.FragmentCartBinding
import uz.alijonov.foodexpress.model.EventModel
import uz.alijonov.foodexpress.utils.Constants
import uz.alijonov.foodexpress.utils.Prefs
import uz.alijonov.foodexpress.view.adapter.FoodAdapter
import uz.alijonov.foodexpress.base.formattedAmount
import uz.alijonov.foodexpress.base.startActivity

class CartFragment : BaseFragment<FragmentCartBinding>() {
    companion object {

        @JvmStatic
        fun newInstance() =
            CartFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun getViewBinding(): FragmentCartBinding {
        return FragmentCartBinding.inflate(layoutInflater)
    }

    override fun setupViews() {

        if (Prefs.getCartList().isNotEmpty()) {

            binding.scrollView.visibility = View.VISIBLE
            binding.lyEmptyCart.visibility = View.GONE
            binding.tvCartCount.text = "${Prefs.getCartList().count()} items"
            binding.recOrders.layoutManager = GridLayoutManager(activity, 2)
            binding.recOrders.adapter =
                FoodAdapter(Prefs.getCartList(), object : BaseAdapterListener {
                    override fun onClickItem(item: Any?) {

                    }
                })

            var totalAmount = 0.0
            Prefs.getCartList().forEach {
                totalAmount += it.price * it.cart_count
            }
            binding.tvTotalPrice.text = totalAmount.formattedAmount()
        } else {
            binding.lyEmptyCart.visibility = View.VISIBLE
            binding.scrollView.visibility = View.GONE
        }

        binding.btnOrder.setOnClickListener {
            activity?.startActivity<CheckoutActivity>()
        }
    }

    override fun loadData() {

    }

    override fun setData() {

    }

    override fun onResume() {
        super.onResume()
        setupViews()
    }

    @Subscribe
    fun onEvent(event: EventModel<Any>) {
        if (event.event == Constants.EVENT_UPDATE_CART) {
            setupViews()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }
}