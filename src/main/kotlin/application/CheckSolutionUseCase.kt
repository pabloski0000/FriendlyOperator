package application

import domain.Operand
import domain.Operation

class CheckSolutionUseCase {
    suspend fun check(operation: Operation, solution: Operand): Boolean {
        return solution == operation.getResult()
    }
}