package ui

import AppRunner
import org.junit.jupiter.api.Test
import java.lang.Exception

class ProducingRealRandomOperationTest {
    private val randomOperationProducer = AppRunner().run().randomOperationProducer
    private val operationsClassification = generateOperationsClassificationDataStructure(
        minimumOperand = 2,
        maximumOperand = 9,
        )

    @Test
    fun itIsProducingReallyRandomOperations() {
        val numberOfOperations = 1000
        val operations = generateOperations(numberOfOperations)
        val orderedPairs = getOrderedPairsFromOperations(operations)
        classifyOrderedPairs(orderedPairs)
        assertOrderedPairsAreDistributedMoreOrLessEqually(numberOfOperations)
    }

    private fun generateOperationsClassificationDataStructure(
        minimumOperand: Int,
        maximumOperand: Int,
    ): MutableMap<Pair<Int, Int>, Int> {
        val operationClassification = mutableMapOf<Pair<Int, Int>, Int>()
        for (i in minimumOperand .. maximumOperand) {
            for (j in minimumOperand .. maximumOperand) {
                val orderedPair = Pair(i, j)
                operationClassification[orderedPair] = 0
            }
        }
        return operationClassification
    }

    private fun generateOperations(numberOfOperations: Int): List<String> {
        val operations = mutableListOf<String>()
        for (i in 0 until numberOfOperations) {
            operations.add(randomOperationProducer.getRandomOperation())
        }
        return operations
    }


    private fun classifyOrderedPairs(orderedPairs: List<Pair<Int, Int>>) {
        orderedPairs.map { orderedPair ->
            operationsClassification[orderedPair] = operationsClassification[orderedPair]!! + 1
        }
    }

    private fun assertOrderedPairsAreDistributedMoreOrLessEqually(numberOfOperations: Int) {
        val perfectPercentageOfAppearance = 100.0 / operationsClassification.size
        val allowedError = 5
        val minimumAppearancePercentage = perfectPercentageOfAppearance - allowedError
        val maximumAppearancePercentage = perfectPercentageOfAppearance + allowedError
        for ((orderedPair, numberOfTimesThatHasAppeared) in operationsClassification) {
            val orderedPairPercentageAppearance = numberOfTimesThatHasAppeared.toDouble() / numberOfOperations * 100
            if (orderedPairPercentageAppearance !in minimumAppearancePercentage..maximumAppearancePercentage) {
                throw Exception("The ordered pair $orderedPair appears $orderedPairPercentageAppearance%" +
                        " ($numberOfTimesThatHasAppeared times) but should appear between" +
                        " $minimumAppearancePercentage% (${minimumAppearancePercentage / 100 * numberOfOperations} times) and" +
                        " $maximumAppearancePercentage% (${maximumAppearancePercentage / 100 * numberOfOperations} times), both" +
                        " included")
            }
        }
    }

    private fun getOrderedPairsFromOperations(operations: List<String>): List<Pair<Int, Int>> {
        val orderedPairsFromOperations = mutableListOf<Pair<Int, Int>>()
        operations.map { operation ->
            orderedPairsFromOperations.add(getOrderedPairFromAOperation(operation))
        }
        return orderedPairsFromOperations
    }

    private fun getOrderedPairFromAOperation(operation: String): Pair<Int, Int> {
        return Pair(operation[0].digitToInt(), operation[2].digitToInt())
    }
}