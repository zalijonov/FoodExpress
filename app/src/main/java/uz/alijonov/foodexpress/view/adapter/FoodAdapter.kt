package uz.alijonov.foodexpress.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import org.greenrobot.eventbus.EventBus
import uz.alijonov.foodexpress.base.BaseAdapter
import uz.alijonov.foodexpress.base.BaseAdapterListener
import uz.alijonov.foodexpress.databinding.FoodItemLayoutBinding
import uz.alijonov.foodexpress.model.EventModel
import uz.alijonov.foodexpress.model.ProductModel
import uz.alijonov.foodexpress.utils.Constants
import uz.alijonov.foodexpress.utils.Prefs
import uz.alijonov.foodexpress.base.formattedAmount
import uz.alijonov.foodexpress.base.loadImage

class FoodAdapter(val list: List<ProductModel>, val handler: BaseAdapterListener) :
    BaseAdapter<FoodItemLayoutBinding>(list.toMutableList(), handler) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemHolder<FoodItemLayoutBinding> {
        return ItemHolder(
            FoodItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder<FoodItemLayoutBinding>, position: Int) {
        super.onBindViewHolder(holder, position)

        val item = items[position] as ProductModel
        holder.binding.imgFood.loadImage(item.image)
        holder.binding.tvName.text = item.name
        holder.binding.tvPrice.text = "${item.price.formattedAmount()} so'm"
        holder.binding.tvCount.text = item.cart_count.toString()

        holder.binding.imgPlus.setOnClickListener {
            var count = holder.binding.tvCount.text.toString().toIntOrNull() ?: 0
            count++

            item.cart_count = count
            holder.binding.tvCount.text
            Prefs.add2Cart(item)
            updateCart()
            notifyDataSetChanged()
        }

        holder.binding.imgMinus.setOnClickListener {
            var count = holder.binding.tvCount.text.toString().toIntOrNull() ?: 0
            count--
            if (count < 0) {
                count = 0
            }
            item.cart_count = count
            holder.binding.tvCount.text
            Prefs.add2Cart(item)
            updateCart()
            notifyDataSetChanged()
        }
    }

    fun updateCart(){
        EventBus.getDefault().post(EventModel(Constants.EVENT_UPDATE_CART, 0))
    }
}