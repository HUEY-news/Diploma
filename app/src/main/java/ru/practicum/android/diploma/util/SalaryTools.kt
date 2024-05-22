package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.details.domain.model.Salary

fun getSalary(
    context: Context,
    salary: Salary?
): String {
    return when {
        salary == null -> context.getString(R.string.salary_not_specified)
        salary.from != null && salary.to != null -> context.getString(
            R.string.salary_from_to,
            salary.from,
            salary.to,
            salary.currency
        )

        salary.from != null -> context.getString(
            R.string.salary_from,
            salary.from,
            salary.currency
        )

        salary.to != null -> context.getString(
            R.string.salary_to,
            salary.to,
            salary.currency
        )

        else -> context.getString(R.string.salary_not_specified)
    }
}
