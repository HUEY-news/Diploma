package ru.practicum.android.diploma.filter.ui.industry

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemIndustryBinding
import ru.practicum.android.diploma.filter.domain.model.Industry

class IndustyViewHolder(
    private val binding: ItemIndustryBinding,
    onItemClick: (position: Int) -> Unit,
    private val onRadioButtonClick: (position: Int) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        itemView.setOnClickListener {
            onItemClick(adapterPosition)
        }
        binding.filterIndustryItem.setOnClickListener {
            onRadioButtonClick(adapterPosition)
        }
    }

    fun bind(industry: Industry, onIndustryClickListener: (industry: Industry, position: Int) -> Unit) {
        binding.filterIndustryItem.text = industry.name
        binding.filterIndustryItem.isChecked = industry.isChecked
        binding.filterIndustryItem.setOnClickListener { onIndustryClickListener(industry, adapterPosition) }
    }
}
