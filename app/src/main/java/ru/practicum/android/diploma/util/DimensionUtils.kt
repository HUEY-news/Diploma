package ru.practicum.android.diploma.util

import android.content.res.Resources
import android.util.TypedValue

fun dpToPx(dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
}
