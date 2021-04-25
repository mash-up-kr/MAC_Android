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
    private var itemPosition: Int = -1

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounselingViewHolder {
        return CounselingViewHolder(parent).apply {
            itemView.setOnClickListener {
                onItemClickListener?.onClick(items[layoutPosition].id)
            }
        }
    }

    override fun onBindViewHolder(holder: CounselingViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

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

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun replaceAll(items: List<CounselingItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setScrollPositionItem(itemPosition: Int) {
        if (this.itemPosition != itemPosition) {
            this.itemPosition = itemPosition
            onItemClickListener?.onScrollItem(items[itemPosition].id)
        }
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
        fun onScrollItem(id: Int)
    }
}
