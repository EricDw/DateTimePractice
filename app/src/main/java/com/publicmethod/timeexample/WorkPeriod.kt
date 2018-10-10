package com.publicmethod.timeexample

import android.annotation.SuppressLint
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

data class WorkPeriod(
    private var initialStartDateTime: LocalDateTime,
    val endDateTime: LocalDateTime
) {

    val startDateTime: LocalDateTime
        get() = initialStartDateTime

    @SuppressLint("NewApi")
    fun split(): Optional<WorkPeriod> =
        split(
            startDateTime
                .toLocalDate()
                .plusDays(1)
                .atStartOfDay()
        )

    @SuppressLint("NewApi")
    fun split(splitTime: LocalDateTime): Optional<WorkPeriod> =
        when (startDateTime.isBefore(splitTime)
                && splitTime.isBefore(endDateTime)) {

            true -> {
                val result = Optional.of(
                    WorkPeriod(
                        startDateTime,
                        Duration.between(startDateTime, splitTime)
                    )
                )
                initialStartDateTime = splitTime
                result
            }

            false ->
                Optional.empty()
        }

    @SuppressLint("NewApi")
    constructor(
        startDateTime: LocalDateTime,
        duration: Duration
    ) : this(
        initialStartDateTime = startDateTime,
        endDateTime = startDateTime.plus(duration)
    )


}
