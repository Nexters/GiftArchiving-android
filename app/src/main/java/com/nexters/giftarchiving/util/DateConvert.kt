package com.nexters.giftarchiving.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateConvert {
    private const val LOCAL_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS"

    fun localDateToLocalDateTimeStr(localDate: LocalDate): String {
        val dateTimeFormatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT)
        return localDate.atStartOfDay().format(dateTimeFormatter)
    }
}