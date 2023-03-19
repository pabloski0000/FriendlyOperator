package domain

sealed interface Operation {
    fun getFirstOperand(): Operand
    fun getSecondOperand(): Operand
    fun getResult(): Operand
    data class Multiplication private constructor(
        private val firstOperand: Operand,
        private val secondOperand: Operand
    ) : Operation {
        companion object {
            fun generateRandomOne(): Multiplication {
                return Multiplication(
                    Operand.fromInt(getRandomDigit()),
                    Operand.fromInt(getRandomDigit()),
                )
            }

            private fun getRandomDigit(): Int {
                return listOf(2,3,4,5,6,7,8,9).random()
            }
        }

        override fun getFirstOperand(): Operand {
            return firstOperand
        }

        override fun getSecondOperand(): Operand {
            return secondOperand
        }

        override fun getResult(): Operand {
            return Operand.fromDouble(
                firstOperand.toDouble() * secondOperand.toDouble(),
            )
        }

        override fun toString(): String {
            return "$firstOperand*$secondOperand"
        }
    }
}
