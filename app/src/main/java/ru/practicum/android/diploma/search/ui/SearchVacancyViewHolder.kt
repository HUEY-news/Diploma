package ru.practicum.android.diploma.search.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy


class SearchVacancyViewHolder
    (private var binding: ItemVacancyBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: SimpleVacancy) {
        binding.jobTitle.text = model.name
        binding.employer.text = model.employer!!.name
        binding.salary.text = getSalary(model.salary)
        Glide.with(itemView)
            .load(model.employer.logoUrls)
            .placeholder(R.drawable.icon_android_placeholder)
            .centerCrop()
            .into(binding.vacancyCover)
    }

    fun setOnVacancyListener(listener: onVacancyClickListener) {
        itemView.setOnClickListener {
            listener.action()
        }
    }

    fun getSalary(salary: Salary?): String {
        var stringSalary = ""
        if ((salary == null) || (salary.from == null && salary.to == null)) {
            stringSalary = R.string.salary_not_specified.toString()
        } else if (salary.from != null) {
            stringSalary += "${R.string.from} ${salary.from}"
            if (salary.to != null) {
                stringSalary += "${R.string.to} ${salary.to}"
            }
        }
        return stringSalary
    }
}

interface onVacancyClickListener {
    fun action()
}

