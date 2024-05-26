package ru.practicum.android.diploma.filter.ui.area

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemCountryBinding
import ru.practicum.android.diploma.filter.domain.model.Country

class CountryAdapter(private val itemClickListener: ItemClickListener) :
    ListAdapter<Country, RecyclerView.ViewHolder>(IndustryDiffCallBack()) {
    private var areas: MutableList<Country> = mutableListOf()
    fun setItems(items: List<Country>) {
        areas.clear()
        areas = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return AreaViewHolder(ItemCountryBinding.inflate(layoutInspector, parent, false)) { position: Int ->
            if (position != RecyclerView.NO_POSITION) {
                areas.getOrNull(position)?.let { area ->
                    itemClickListener.onItemClick(area)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        areas.getOrNull(position)?.let { area ->
            (holder as AreaViewHolder).bind(area)
        }
    }

    override fun getItemCount() = areas.size

    fun interface ItemClickListener {
        fun onItemClick(country: Country)
    }

    class IndustryDiffCallBack : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }
    }
}
