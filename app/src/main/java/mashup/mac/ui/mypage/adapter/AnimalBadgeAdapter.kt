package mashup.mac.ui.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import mashup.mac.R
import mashup.mac.databinding.ItemAnimalBadgeBinding
import mashup.mac.model.AnimalBadgeItem
import mashup.mac.model.CounselingItem
import mashup.mac.ui.counseling.adapter.AnimalCategoryAdapter

class AnimalBadgeAdapter(private val listener: (List<CounselingItem>) -> Unit) :
    RecyclerView.Adapter<AnimalBadgeAdapter.AnimalBadgeViewHolder>() {

    private var prePosition = AnimalCategoryAdapter.DEFAULT_CHECK_ITEM_POSITION

    private val items = mutableListOf<AnimalBadgeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalBadgeViewHolder {
        return AnimalBadgeViewHolder(parent).apply {
            itemView.setOnClickListener {
                val currentPosition = adapterPosition
                val item = items[currentPosition]

                if (currentPosition != prePosition) {
                    notifyUnCheckItem(prePosition)
                    notifyCheckItem(currentPosition)
                    changePrePosition(currentPosition)
                    listener.invoke(item.counselingItems)
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

    override fun onBindViewHolder(holder: AnimalBadgeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun replaceAll(items: List<AnimalBadgeItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class AnimalBadgeViewHolder(
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_animal_badge, parent, false)
    ) {

        private val binding: ItemAnimalBadgeBinding? = DataBindingUtil.bind(itemView)

        fun bind(item: AnimalBadgeItem) {
            binding?.item = item
        }
    }
}