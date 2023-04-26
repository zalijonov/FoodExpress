package uz.alijonov.foodexpress.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import uz.alijonov.foodexpress.R

interface BaseAdapterListener {
    fun onClickItem(item: Any?)
}

abstract class BaseAdapter<B : ViewBinding>(
    var items: MutableList<Any?>,
    val listener: BaseAdapterListener? = null
) : RecyclerView.Adapter<BaseAdapter<B>.ItemHolder<B>>() {
    val colors = listOf(
        R.color.color1,
        R.color.color2,
        R.color.color3,
        R.color.color4,
        R.color.color5,
        R.color.color6,
        R.color.color7
    )

    fun getRandomColor(position: Int): Int {
        return colors[position % colors.size]
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ItemHolder<B>, position: Int) {
        holder.itemView.setOnClickListener {
            listener?.onClickItem(items[position])
        }
    }

    fun <T> getItem(position: Int): T {
        return items[position] as T
    }

    inner class ItemHolder<B : ViewBinding>(val binding: B) : RecyclerView.ViewHolder(binding.root)
}