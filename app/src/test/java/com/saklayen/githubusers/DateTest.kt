package com.saklayen.githubusers

import java.time.LocalDate

class DateTest {

    fun testdATE(){

    }
}

fun getPreviousMonth(inputDate: String): String {
    val formatter = DateTimeFormatter.ofPattern("MMM, yyyy")
    val parsedDate = LocalDate.parse(inputDate, formatter)

    // Subtract one month from the parsed date
    val previousMonth = parsedDate.minusMonths(1)

    // Format the result in "MMM, yyyy" format
    val result = previousMonth.format(formatter)

    return result
}