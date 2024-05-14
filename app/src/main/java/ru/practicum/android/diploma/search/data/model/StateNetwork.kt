package ru.practicum.android.diploma.search.data.model

enum class StateNetwork(val code: Int) {
    CONNECTION_ERROR(-1),
    SUCCESS(200),
    NOT_FOUND(400),
    SERVER_ERROR(500)
}
