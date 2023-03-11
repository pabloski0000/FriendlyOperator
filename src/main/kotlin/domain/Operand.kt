package domain

data class Operand private constructor(private val value: Double) {
    companion object {
        fun createItFromInt(value: Int): Operand {
            return Operand(value.toDouble())
        }

        fun createItFromDouble(value: Double): Operand {
            return Operand(value)
        }
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