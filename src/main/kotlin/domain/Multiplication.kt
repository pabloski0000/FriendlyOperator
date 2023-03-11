package domain

data class Multiplication private constructor(private val firstOperand: Operand, private val secondOperand: Operand) {
    companion object {
        fun generateRandomOne(): Multiplication {
            return Multiplication(
                Operand.createItFromInt(getRandomDigit()),
                Operand.createItFromInt(getRandomDigit()),
            )
        }

        private fun getRandomDigit(): Int {
            return listOf(2,3,4,5,6,7,8,9).random()
        }
    }

    fun getResult(): Operand {
        return Operand.createItFromDouble(
            firstOperand.toDouble() * secondOperand.toDouble(),
        )
    }

    override fun toString(): String {
        return "$firstOperand*$secondOperand"
    }
}
