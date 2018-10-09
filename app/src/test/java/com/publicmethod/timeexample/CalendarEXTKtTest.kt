package com.publicmethod.timeexample

import org.junit.Assert
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*

class CalendarEXTKtTest {


    @Test
    fun given_LocalDate_when_generateWorkPeriods_then_return_correct_WorkPeriods_amount() {
        // Arrange
        val input = LocalDate.of(
            2018,
            5,
            24
        )

        val expected: List<Int> = listOf(
            Calendar.THURSDAY,
            Calendar.FRIDAY,
            Calendar.MONDAY
        )

        // Act
        val actual: List<Int> =
            Calendar
                .getInstance()
                .generateWorkPeriods()
                .asSequence()
                .map { it.startTime }
                .map { DayOfWeek.from(it).value }
                .distinct()
                .toList()

        // Assert
        Assert.assertEquals(expected, actual)
    }


}

