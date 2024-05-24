package ru.practicum.android.diploma.filter.ui.industry

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemIndustryBinding
import ru.practicum.android.diploma.filter.domain.model.Industry

class IndustyViewHolder(
    private val binding: ItemIndustryBinding,
    onItemClick: (position: Int) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        itemView.setOnClickListener {
            onItemClick(adapterPosition)
        }
    }

    fun bind(model: Industry) {
        binding.industryRadioButton.text = model.name
    }
}
