package ru.practicum.android.diploma.filter.ui.industry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemIndustryBinding
import ru.practicum.android.diploma.filter.domain.model.Industry

class IndustryAdapter(
    private val itemClickListener: ItemClickListener
) : ListAdapter<Industry, RecyclerView.ViewHolder>(IndustryDiffCallBack()) {

    private var industries: MutableList<Industry> = mutableListOf()
    private var selectedPosition = RecyclerView.NO_POSITION

    fun setPosition(position: Int) {
        selectedPosition = position
    }

    fun setItems(items: List<Industry>) {
        industries.clear()
        industries = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return IndustryViewHolder(
            ItemIndustryBinding.inflate(layoutInflater, parent, false),
            { position: Int ->
                if (position != RecyclerView.NO_POSITION) {
                    industries.getOrNull(position)?.let { industry ->
                        itemClickListener.onItemClick(industry)
                    }
                }
            },
            { position: Int ->
                if (position != RecyclerView.NO_POSITION) {
                    val previousPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)
                    industries.getOrNull(position)?.let { industry ->
                        itemClickListener.onItemClick(industry)
                    }
                }
            }
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        industries.getOrNull(position)?.let { industry ->
            (holder as IndustryViewHolder).bind(industry, position == selectedPosition)
        }
    }

    override fun getItemCount() = industries.size

    fun interface ItemClickListener {
        fun onItemClick(industry: Industry)
    }

    class IndustryDiffCallBack : DiffUtil.ItemCallback<Industry>() {
        override fun areItemsTheSame(oldItem: Industry, newItem: Industry): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Industry, newItem: Industry): Boolean {
            return oldItem == newItem
        }
    }
}
