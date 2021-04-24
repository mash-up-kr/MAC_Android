package mashup.mac.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import mashup.mac.R
import mashup.mac.databinding.ItemCounselingBinding
import mashup.mac.model.CounselingItem

class MainCounselingAdapter :
    RecyclerView.Adapter<MainCounselingAdapter.CounselingViewHolder>() {

    private val items = mutableListOf<CounselingItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounselingViewHolder {
        return CounselingViewHolder(parent).apply {
            itemView.setOnClickListener {
               //..
            }
        }
    }

    override fun onBindViewHolder(holder: CounselingViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun replaceAll(items: List<CounselingItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class CounselingViewHolder(
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_counseling, parent, false)
    ) {

        private val binding: ItemCounselingBinding? = DataBindingUtil.bind(itemView)

        fun bind(item: CounselingItem) {
            binding?.item = item
        }
    }
}