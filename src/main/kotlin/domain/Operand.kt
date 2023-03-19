package domain

import kotlin.math.roundToLong

data class Operand private constructor(private val value: Double) {
    companion object {
        fun fromInt(value: Int): Operand {
            return Operand(value.toDouble())
        }

        fun fromDouble(value: Double): Operand {
            return Operand(value)
        }
    }

    fun hasDecimals(): Boolean {
        val valueWithoutDecimals = value.roundToLong()
        return value.mod(valueWithoutDecimals.toDouble()) != 0.0
    }

    fun toInt(): Int {
        return value.toInt()
    }

    fun toDouble(): Double {
        return value
    }

    override fun toString(): String {
        return value.toString()
    }
}