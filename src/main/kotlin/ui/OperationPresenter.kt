package ui

import domain.Operand
import domain.Operation

class OperationPresenter {
    companion object {
        fun present(presentee: Operation): String {
            val firstOperand = presentOperand(presentee.getFirstOperand())
            val secondOperand = presentOperand(presentee.getSecondOperand())
            val operator = presentOperator(presentee)
            return "$firstOperand$operator$secondOperand="
        }

        private fun presentOperand(operand: Operand): String {
            return if (operand.hasDecimals()) {
                operand.toDouble().toString()
            } else {
                operand.toInt().toString()
            }
        }

        private fun presentOperator(operation: Operation): String {
            return when (operation) {
                is Operation.Multiplication -> "*"
            }
        }
    }
}