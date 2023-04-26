package uz.alijonov.foodexpress.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import uz.alijonov.foodexpress.base.BaseAdapter
import uz.alijonov.foodexpress.base.BaseAdapterListener
import uz.alijonov.foodexpress.databinding.OfferItemLayoutBinding
import uz.alijonov.foodexpress.model.OfferModel
import uz.bdm.base.base.loadImage

class OfferAdapter(val list: List<OfferModel>, val handler: BaseAdapterListener) :
    BaseAdapter<OfferItemLayoutBinding>(list.toMutableList(), handler) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ItemHolder<OfferItemLayoutBinding> {
        return ItemHolder(
            OfferItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder<OfferItemLayoutBinding>, position: Int) {
        super.onBindViewHolder(holder, position)

        val item = items[position] as OfferModel

        holder.binding.image.loadImage(item.image)
        holder.binding.tvTitle.text = item.title
    }
}