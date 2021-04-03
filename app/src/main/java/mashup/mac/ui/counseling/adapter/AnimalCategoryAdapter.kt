package mashup.mac.ui.counseling.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import mashup.mac.R
import mashup.mac.databinding.ItemAnimalCategoryBinding
import mashup.mac.ui.counseling.model.CategoryItem

class AnimalCategoryAdapter :
    RecyclerView.Adapter<AnimalCategoryAdapter.AnimalCategoryViewHolder>() {

    companion object {

        const val DEFAULT_CHECK_ITEM_POSITION = 0
    }

    private var prePosition = DEFAULT_CHECK_ITEM_POSITION

    private val items = mutableListOf<CategoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalCategoryViewHolder {
        return AnimalCategoryViewHolder(parent).apply {
            itemView.setOnClickListener {
                val currentPosition = adapterPosition
                if (currentPosition != prePosition) {
                    notifyUnCheckItem(prePosition)
                    notifyCheckItem(currentPosition)
                    changePrePosition(currentPosition)
                }
            }
        }
    }

    private fun notifyUnCheckItem(position: Int) {
        items[position] = items[position].copy(isCheck = false)
        notifyItemChanged(position)
    }

    private fun notifyCheckItem(position: Int) {
        items[position] = items[position].copy(isCheck = true)
        notifyItemChanged(position)
    }

    private fun changePrePosition(position: Int) {
        prePosition = position
    }

    override fun onBindViewHolder(holder: AnimalCategoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun replaceAll(items: List<CategoryItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun getCheckedCategory() = items.find { it.isCheck }

    class AnimalCategoryViewHolder(
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_animal_category, parent, false)
    ) {

        private val binding: ItemAnimalCategoryBinding? = DataBindingUtil.bind(itemView)

        fun bind(item: CategoryItem) {
            binding?.item = item
        }
    }
}