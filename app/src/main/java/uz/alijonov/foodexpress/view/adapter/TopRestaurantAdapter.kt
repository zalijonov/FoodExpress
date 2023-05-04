package uz.alijonov.foodexpress.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import uz.alijonov.foodexpress.base.BaseAdapter
import uz.alijonov.foodexpress.databinding.TopItemLayoutBinding
import uz.alijonov.foodexpress.model.RestaurantModel
import uz.alijonov.foodexpress.base.loadImage
import java.text.DecimalFormat

class TopRestaurantAdapter(
    val list: List<RestaurantModel>,
    val handler: RestaurantAdapterListener
) : BaseAdapter<TopItemLayoutBinding>(list.toMutableList(), handler) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemHolder<TopItemLayoutBinding> {
        return ItemHolder(
            TopItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder<TopItemLayoutBinding>, position: Int) {
        super.onBindViewHolder(holder, position)

        val item = items[position] as RestaurantModel

        holder.binding.image.loadImage(item.main_image)
        holder.binding.tvTitle.text = item.name
        holder.binding.tvDistance.text = "${DecimalFormat("#.##").format(item.distance)} km"
        holder.binding.tvLocation.text = item.address
        holder.binding.tvRating.text = DecimalFormat("#.#").format(item.rating)
        holder.itemView.setOnClickListener { handler.onClick(item) }
    }
}