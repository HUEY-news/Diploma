package ru.practicum.android.diploma.filter.ui.industry

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemIndustryBinding
import ru.practicum.android.diploma.filter.domain.model.Industry

class IndustryViewHolder(
    private val binding: ItemIndustryBinding,
    onItemClick: (position: Int) -> Unit,
    private val onRadioButtonClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            onItemClick(adapterPosition)
        }

        binding.filterIndustryItem.setOnClickListener {
            onRadioButtonClick(adapterPosition)
        }
    }

    fun bind(model: Industry, isSelected: Boolean) {
        binding.filterIndustryItem.text = model.name
        binding.filterIndustryItem.isChecked = isSelected
    }
}
