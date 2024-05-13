package ru.practicum.android.diploma.util

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.search.domain.model.Vacancy

class DiffUtilCallback(private val oldContent: List<Vacancy>, private val newContent: List<Vacancy>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldContent.size
    }

    override fun getNewListSize(): Int {
        return newContent.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldContent[oldItemPosition] == newContent[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldContent[oldItemPosition].vacancyId == newContent[newItemPosition].vacancyId
    }
}
