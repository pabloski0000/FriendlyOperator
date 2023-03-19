package ui

import AppRunner
import org.junit.jupiter.api.Test
import java.lang.Exception

class ProducingRealRandomOperationTest {
    private val randomOperationProducer = AppRunner().run().randomOperationProducer

    @Test
    fun itIsProducingReallyRandomOperations() {
        val numberOfOperations = 1000
        val operations = generateOperations(numberOfOperations)
        val operationClassification = classifyOperations(operations)
        assertOperationsAreGeneratedRandomly(numberOfOperations, operationClassification)
    }

    private fun classifyOperations(operations: List<String>): MutableMap<Pair<Int, Int>, Int> {
        val orderedPairs = extractOrderedPairsFromOperations(operations)
        return classifyOrderedPairs(orderedPairs)
    }

    private fun classifyOrderedPairs(orderedPairs: List<Pair<Int, Int>>): MutableMap<Pair<Int, Int>, Int> {
        val orderedPairTable = initialiseOrderedPairTable(
            minimumOperand = 2,
            maximumOperand = 9,
        )
        return fillOutTableWithOrderedPairs(orderedPairs, orderedPairTable)
    }

    private fun fillOutTableWithOrderedPairs(
        orderedPairs: List<Pair<Int, Int>>,
        table: MutableMap<Pair<Int, Int>, Int>,
    ): MutableMap<Pair<Int, Int>, Int> {
        orderedPairs.map { orderedPair ->
            table[orderedPair] = table[orderedPair]!! + 1
        }
        return table
    }

    private fun initialiseOrderedPairTable(
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

    private fun assertOperationsAreGeneratedRandomly(
        numberOfOperations: Int,
        operationClassification: MutableMap<Pair<Int, Int>, Int>,
    ) {
        val perfectPercentageOfAppearance = 100.0 / operationClassification.size
        val allowedError = 5
        val minimumAppearancePercentage = perfectPercentageOfAppearance - allowedError
        val maximumAppearancePercentage = perfectPercentageOfAppearance + allowedError
        for ((orderedPair, numberOfTimesThatHasAppeared) in operationClassification) {
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

    private fun extractOrderedPairsFromOperations(operations: List<String>): List<Pair<Int, Int>> {
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