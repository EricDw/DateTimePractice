package com.publicmethod.timeexample


import android.annotation.SuppressLint
import java.time.*
import java.util.*


@SuppressLint("NewApi")
private val workDayStartTime: LocalTime =
    LocalTime.of(9, 0)

@SuppressLint("NewApi")
private val lunchStartTime: LocalTime =
    LocalTime.of(12, 0)

@SuppressLint("NewApi")
private val morningWorkDuration: Duration =
    Duration.between(workDayStartTime, lunchStartTime)

@SuppressLint("NewApi")
private val lunchEndTime: LocalTime =
    LocalTime.of(13, 0)

@SuppressLint("NewApi")
private val lunchDuration: Duration =
    Duration.between(lunchStartTime, lunchEndTime)

@SuppressLint("NewApi")
private val workDayEndTime: LocalTime =
    LocalTime.of(17, 0)

@SuppressLint("NewApi")
private val afternoonWorkDuration: Duration =
    Duration.between(lunchEndTime, workDayEndTime)

@SuppressLint("NewApi")
fun Calendar.generateWorkPeriods(
    localDate: LocalDate,
    amountOfDays: Int
): List<WorkPeriod> {

    val workingDays =
        generateWorkingDays(localDate, amountOfDays)

    return generateWorkPeriods(
        workingDays,
        workDayStartTime,
        morningWorkDuration,
        workDayEndTime,
        afternoonWorkDuration
    )

}

@SuppressLint("NewApi")
private fun Calendar.generateWorkPeriods(
    workingDays: List<LocalDate>,
    workDayStartTime: LocalTime,
    startDayDuration: Duration,
    workDayEndTime: LocalTime,
    endDayDuration: Duration
): List<WorkPeriod> {

    val workPeriods: MutableList<WorkPeriod> =
        mutableListOf()

    workingDays.forEach {
        val startDateTime = LocalDateTime.of(it, workDayStartTime)
        val endDateTime = LocalDateTime.of(it, workDayEndTime)
        workPeriods.add(WorkPeriod(startDateTime, startDateTime.plus(startDayDuration)))
        workPeriods.add(WorkPeriod(endDateTime, endDateTime.plus(endDayDuration)))
    }

    return workPeriods
}

@SuppressLint("NewApi")
private fun Calendar.generateWorkingDays(
    startDate: LocalDate,
    amountOfDays: Int
): List<LocalDate> =
    generateSequence(startDate) { sd ->
        sd.plusDays(1)
    }.filter(::isWeekDay)
        .take(amountOfDays)
        .toList()

@SuppressLint("NewApi")
private fun Calendar.isWeekDay(
    localDate: LocalDate
): Boolean =
    localDate.dayOfWeek != DayOfWeek.SATURDAY
            && localDate.dayOfWeek != DayOfWeek.SUNDAY

