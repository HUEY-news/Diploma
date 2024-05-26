package ru.practicum.android.diploma.filter.ui.area

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemCountryBinding
import ru.practicum.android.diploma.filter.domain.model.Country

class AreaViewHolder(
    private val binding: ItemCountryBinding,
    onItemClick: (position: Int) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        itemView.setOnClickListener {
            onItemClick(adapterPosition)
        }
    }

    fun bind(model: Country) {
        binding.filterCountryItem.text = model.name
    }
}
