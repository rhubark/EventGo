package com.rhuan.eventgo.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Formats {
    companion object {

        private var locale = Locale("pt", "BR")
        private val formatDayMonthSimplified = SimpleDateFormat("EEE',' MMM dd '─' HH:mm", locale)
        private val formatDayMonthExtensive = SimpleDateFormat("EEEE',' dd MMMM 'às' HH:mm", locale)
        private val formatHour = SimpleDateFormat("HH:mm", locale)
        private val formatMoney = NumberFormat.getCurrencyInstance(locale)
        private val formatMonth = SimpleDateFormat("MMMM", locale)
        private val formatDay = SimpleDateFormat("dd", locale)

        private fun typeConversion(time: Long?) = time?.let { Date(it) } ?: run { "" }

        fun longToDateSimplified(time: Long?): String {
            val date = typeConversion(time)
            return formatDayMonthSimplified.format(date)
        }

        fun longToDateExtensive(time: Long?): String {
            val date = typeConversion(time)
            return formatDayMonthExtensive.format(date)
        }

        fun shortDate(time: Long?): String {
            val date = typeConversion(time)
            return formatDay.format(date)
        }

        fun longMonth(time: Long?): String {
            val date = typeConversion(time)
            return formatMonth.format(date)
        }

        fun longToHour(time: Long?): String {
            val date = typeConversion(time)
            return formatHour.format(date)
        }

        fun money(value: BigDecimal?): String {
            return value?.let { formatMoney.format(it.toDouble()) } ?: run { "" }
        }

    }
}