package ru.practicum.android.diploma.filter.ui.region

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemCountryBinding
import ru.practicum.android.diploma.filter.domain.model.Area

class RegionAdapter(
    private val itemClickListener: ItemClickListener
) : ListAdapter<Area, RecyclerView.ViewHolder>(CountryDiffCallBack()) {

    private var areas: MutableList<Area> = mutableListOf()

    fun setItems(items: List<Area>) {
        areas.clear()
        areas = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return RegionViewHolder(ItemCountryBinding.inflate(layoutInspector, parent, false)) { position: Int ->
            if (position != RecyclerView.NO_POSITION) {
                areas.getOrNull(position)?.let { area ->
                    itemClickListener.onItemClick(area)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        areas.getOrNull(position)?.let { area ->
            (holder as RegionViewHolder).bind(area)
        }
    }

    override fun getItemCount() = areas.size

    fun interface ItemClickListener {
        fun onItemClick(country: Area)
    }

    class CountryDiffCallBack : DiffUtil.ItemCallback<Area>() {
        override fun areItemsTheSame(oldItem: Area, newItem: Area): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Area, newItem: Area): Boolean {
            return oldItem == newItem
        }
    }
}
