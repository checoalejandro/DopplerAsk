package com.oracle.doppler.ask.models

data class Column(
        val name: String,
        val locale: String = "en_us",
        val type: ColumnTypes = ColumnTypes.UNKNOWN,
        val length: Long? = null,
        val decimals: Long? = null,
        val isGeo: Boolean = false,
        val isTimeBased: Boolean = false
)