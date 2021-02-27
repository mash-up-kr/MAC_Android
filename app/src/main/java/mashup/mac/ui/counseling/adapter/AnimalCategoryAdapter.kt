package mashup.mac.ui.counseling.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import mashup.mac.R
import mashup.mac.databinding.ItemAnimalCategoryBinding

class AnimalCategoryAdapter :
    RecyclerView.Adapter<AnimalCategoryAdapter.AnimalCategoryViewHolder>() {

    private val items = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalCategoryViewHolder {
        return AnimalCategoryViewHolder(parent)
    }

    override fun onBindViewHolder(holder: AnimalCategoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun replaceAll(items: List<String>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class AnimalCategoryViewHolder(
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_animal_category, parent, false)
    ) {

        private val binding: ItemAnimalCategoryBinding? = DataBindingUtil.bind(itemView)

        fun bind(item: String) {
            binding?.title = item
        }
    }
}