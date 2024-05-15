package ru.practicum.android.diploma.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy

class SearchVacancyAdapter(
    private val onItemClick: (vacancy: SimpleVacancy) -> Unit
) : RecyclerView.Adapter<SearchVacancyViewHolder>() {

    private var vacancyList: List<SimpleVacancy> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVacancyViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return SearchVacancyViewHolder(ItemVacancyBinding.inflate(layoutInspector, parent, false))
        { position: Int ->
            if (position != RecyclerView.NO_POSITION) {
                vacancyList.getOrNull(position)?.let { vacancy ->
                    onItemClick(vacancy)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: SearchVacancyViewHolder, position: Int) {
        vacancyList.getOrNull(position)?.let { vacancy ->
            holder.bind(vacancy)
        }
    }

    override fun getItemCount() = vacancyList.size
}
