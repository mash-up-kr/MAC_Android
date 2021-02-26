package mashup.mac.ui.sample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import mashup.data.sample.model.GithubUserModel
import mashup.mac.R
import mashup.mac.databinding.ItemPersonBinding

class GithubUserAdapter : RecyclerView.Adapter<GithubUserAdapter.PersonHolder>() {

    private val items = mutableListOf<GithubUserModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
        return PersonHolder(parent).apply {
            itemView.setOnClickListener {
                Toast.makeText(
                    it.context,
                    items[adapterPosition].name?.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onBindViewHolder(holder: PersonHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun replaceAll(items: List<GithubUserModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class PersonHolder(
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_person, parent, false)
    ) {

        private val binding: ItemPersonBinding? = DataBindingUtil.bind(itemView)

        fun bind(item: GithubUserModel) {
            binding?.item = item
        }
    }
}