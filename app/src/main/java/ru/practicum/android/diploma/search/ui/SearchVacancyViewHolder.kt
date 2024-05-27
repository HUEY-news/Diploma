package ru.practicum.android.diploma.search.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.Currency
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.details.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy
import ru.practicum.android.diploma.util.dpToPx
import java.text.NumberFormat
import java.util.Locale

class SearchVacancyViewHolder(
    private val binding: ItemVacancyBinding,
    onItemClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            onItemClick(adapterPosition)
        }
    }

    fun bind(model: SimpleVacancy) {
        binding.jobTitle.text = model.name
        binding.employer.text = model.employer!!.name
        binding.salary.text = getSalary(model.salary)

        Glide.with(itemView)
            .load(model.employer.logoUrls)
            .placeholder(R.drawable.icon_android_placeholder)
            .transform(CenterCrop(), RoundedCorners(dpToPx(RADIUS_IN_DP)))
            .into(binding.vacancyCover)
    }

    @SuppressLint("StringFormatMatches")
    private fun getSalary(salary: Salary?): String {
        val locale = Locale("ru")
        val numberFormat = NumberFormat.getInstance(locale)

        val currencySymbol = salary?.currency?.let {
            try {
                Currency.valueOf(it).symbol
            } catch (e: IllegalArgumentException) {
                it
            }
        } ?: ""

        return when {
            salary == null -> itemView.context.getString(R.string.salary_not_specified)
            salary.from != null && salary.to != null -> itemView.context.getString(
                R.string.salary_from_to,
                numberFormat.format(salary.from),
                numberFormat.format(salary.to),
                currencySymbol
            )
            salary.from != null -> itemView.context.getString(
                R.string.salary_from,
                numberFormat.format(salary.from),
                currencySymbol
            )
            salary.to != null -> itemView.context.getString(
                R.string.salary_to,
                numberFormat.format(salary.to),
                currencySymbol
            )
            else -> itemView.context.getString(R.string.salary_not_specified)
        }
    }

    companion object {
        private const val RADIUS_IN_DP = 8
    }
}

