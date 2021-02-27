package mashup.mac.ui.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import mashup.mac.R
import mashup.mac.databinding.ItemAnimalBadgeBinding
import mashup.mac.model.AnimalBadgeItem

class AnimalBadgeAdapter :
    RecyclerView.Adapter<AnimalBadgeAdapter.AnimalBadgeViewHolder>() {

    private val items = mutableListOf<AnimalBadgeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalBadgeViewHolder {
        return AnimalBadgeViewHolder(parent).apply {
            itemView.setOnClickListener {
                Toast.makeText(it.context, "hello", Toast.LENGTH_LONG).show()
            }
        }
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