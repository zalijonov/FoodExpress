package uz.alijonov.foodexpress.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import uz.alijonov.foodexpress.base.BaseAdapter
import uz.alijonov.foodexpress.base.BaseAdapterListener
import uz.alijonov.foodexpress.databinding.NearbyItemLayoutBinding
import uz.alijonov.foodexpress.model.RestaurantModel
import uz.alijonov.foodexpress.base.loadImage
import java.text.DecimalFormat

interface RestaurantAdapterListener : BaseAdapterListener {
    fun onClick(item: RestaurantModel)
}

class NearbyRestaurantAdapter(
    val list: List<RestaurantModel>,
    val handler: RestaurantAdapterListener
) : BaseAdapter<NearbyItemLayoutBinding>(list.toMutableList(), handler) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemHolder<NearbyItemLayoutBinding> {
        return ItemHolder(
            NearbyItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder<NearbyItemLayoutBinding>, position: Int) {
        super.onBindViewHolder(holder, position)

        val item = items[position] as RestaurantModel

        holder.binding.image.loadImage(item.main_image)
        holder.binding.tvTitle.text = item.name
        holder.binding.tvDistance.text = "${DecimalFormat("#.##").format(item.distance)} km"
        holder.itemView.setOnClickListener { handler.onClick(item) }
    }
}