package ui

import kotlin.math.roundToLong

data class Time private constructor (private val milliseconds: Long) {
    companion object {
        fun fromMilliseconds(milliseconds: Long): Time {
            return Time(milliseconds)
        }

        fun fromSeconds(seconds: Double): Time {
            return Time((seconds * 1000).roundToLong())
        }
    }

    fun toMilliseconds(): Long {
        return milliseconds
    }

    fun toSeconds(): Long {
        return milliseconds / 1000
    }
}
