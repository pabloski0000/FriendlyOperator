package domain

data class TimeBeforeOperationGetsSolved private constructor(private val seconds: Double){
    companion object {
        fun fromDouble(seconds: Double): TimeBeforeOperationGetsSolved {
            return TimeBeforeOperationGetsSolved(seconds)
        }
    }

    fun toDouble(): Double {
        return seconds
    }
}