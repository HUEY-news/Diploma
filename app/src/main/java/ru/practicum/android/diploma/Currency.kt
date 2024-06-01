package ru.practicum.android.diploma

enum class Currency(val code: String, val symbol: String) {
    RUR("RUR", "₽"),
    BYR("BYR", "Br"),
    USD("USD", "$"),
    EUR("EUR", "€"),
    KZT("KZT", "₸"),
    UAH("UAH", "₴"),
    AZN("AZN", "₼"),
    UZS("UZS", "сум"),
    GEL("GEL", "ლ"),
    KGT("KGT", "лв");

    companion object {
        fun fromCode(code: String): Currency? {
            return values().find { it.code.equals(code, ignoreCase = true) }
        }
    }
}
