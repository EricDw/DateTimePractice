package com.publicmethod.timeexample

import org.junit.Assert
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.util.*

class CalendarEXTKtTest {


    @Test
    fun given_LocalDate_when_generateWorkPeriods_then_return_correct_WorkPeriods_amount() {
        // Arrange
        val input = LocalDate.of(
            2018,
            Month.OCTOBER,
            4
        ) to 3

        val expected: List<DayOfWeek> = listOf(
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.MONDAY
        )

        // Act
        val actual: List<DayOfWeek> =
            Calendar
                .getInstance()
                .generateWorkPeriods(input.first, input.second)
                .asSequence()
                .map { it.startDateTime }
                .map { DayOfWeek.from(it) }
                .distinct()
                .toList()

        // Assert
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun given_TooLong_WorkPeriod_when_split_then_return_new_period() {
        // Arrange
        val todayAt11PM: LocalDateTime =
            LocalDate.now().atTime(23, 0)

        val tomorrowAt1AM: LocalDateTime =
            LocalDate.now().plusDays(1).atTime(1, 0)

        val midnight: LocalDateTime =
            LocalDate.now().plusDays(1).atStartOfDay()

        val originalPeriod =
            WorkPeriod(todayAt11PM, tomorrowAt1AM)

        // Act
        val split =
            originalPeriod.split().get()

        val expectedOriginalAfterSplit =
            WorkPeriod(midnight, tomorrowAt1AM)
        val expectedSplit =
            WorkPeriod(todayAt11PM, midnight)

        // Assert
        Assert.assertEquals(expectedSplit, split)
        Assert.assertEquals(expectedOriginalAfterSplit, originalPeriod)
    }


}

