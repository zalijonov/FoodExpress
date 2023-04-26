package uz.alijonov.foodexpress.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import uz.alijonov.foodexpress.base.BaseAdapter
import uz.alijonov.foodexpress.base.BaseAdapterListener
import uz.alijonov.foodexpress.databinding.CategoryItemLayoutBinding
import uz.alijonov.foodexpress.model.CategoryModel
import uz.bdm.base.base.loadImage

class CategoryAdapter(val list: List<CategoryModel>, val handler: BaseAdapterListener) :
    BaseAdapter<CategoryItemLayoutBinding>(list.toMutableList(), handler) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemHolder<CategoryItemLayoutBinding> {
        return ItemHolder(
            CategoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder<CategoryItemLayoutBinding>, position: Int) {
        super.onBindViewHolder(holder, position)

        val item = items[position] as CategoryModel
        val context = holder.itemView.context
        val view = holder.binding

        view.image.loadImage(item.image)
        view.tvTitle.text = item.title
        view.btnMore.setOnClickListener { handler.onClickItem(item) }
        holder.itemView.setOnClickListener { handler.onClickItem(item) }
    }
}