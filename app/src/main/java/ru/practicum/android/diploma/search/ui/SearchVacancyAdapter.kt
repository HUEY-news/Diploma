package ru.practicum.android.diploma.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy

class SearchVacancyAdapter(val vacancyClickListener: SearchVacancyAdapter.VacancyClickListener) :
    RecyclerView.Adapter<SearchVacancyViewHolder>() {
    var vacancy = ArrayList<SimpleVacancy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVacancyViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return SearchVacancyViewHolder(ItemVacancyBinding.inflate(layoutInspector, parent, false))
    }

    override fun onBindViewHolder(holder: SearchVacancyViewHolder, position: Int) {
        val item = vacancy[position]
        holder.bind(item)
        holder.setOnVacancyListener(object : onVacClickListener {
            override fun actionClick() {
                // Log.d("Adapter", "OnVacancyClickListener $item ")
                vacancyClickListener.onVacancyClick(item)
            }
        })
    }

    override fun getItemCount() = vacancy.size

    fun interface VacancyClickListener {
        fun onVacancyClick(vacancy: SimpleVacancy)
    }
}
