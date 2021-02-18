package com.nexters.giftarchiving.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateConvert {
    private const val EMPTY_STRING = ""
    private const val LOCAL_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    private const val LOCAL_DATE_TIME_WITH_MILLI_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS"
    private const val LOCAL_DATE_FORMAT1 = "yyyy.MM.dd"

    fun localDateToLocalDateTimeStr(localDate: LocalDate): String {
        try {
            val dateTimeFormatter = getFormatter(LOCAL_DATE_TIME_WITH_MILLI_FORMAT)
            return localDate.atStartOfDay().format(dateTimeFormatter)
        } catch (e: DateTimeParseException) {
        }
        return EMPTY_STRING
    }

    fun localDateTimeStrToLocalDate(dateStr: String): LocalDate {
        try {
            val formatter = getFormatter(LOCAL_DATE_TIME_FORMAT)
            return LocalDate.parse(dateStr, formatter)
        } catch (e: DateTimeParseException) {
        }
        return LocalDate.now()
    }

    fun localDateTimeStrToLocalDateStr1(dateStr: String): String {
        try {
            val formatter = getFormatter(LOCAL_DATE_TIME_FORMAT)
            val localDate = LocalDateTime.parse(dateStr, formatter)
            return localDate.format(getFormatter(LOCAL_DATE_FORMAT1))
        } catch (e: DateTimeParseException) {
        }
        return EMPTY_STRING
    }

    private fun getFormatter(format: String) = DateTimeFormatter.ofPattern(format)
}