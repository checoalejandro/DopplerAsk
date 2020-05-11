package com.oracle.doppler.ask.models

enum class ColumnTypes(val strValue: String) {
    TEXT("text"),
    NUMBER("number"),
    CURRENCY("currency"),
    DATE("date"),
    PERCENT("percent"),
    UNKNOWN("unknown")
}