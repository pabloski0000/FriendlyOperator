package ui

class RandomOperationProducer {

    fun getRandomOperation(): String {
        return "${getRandomDigit()}${getRandomOperator()}${getRandomDigit()}"
    }

    private fun getRandomDigit(): Int {
        return listOf(2,3,4,5,6,7,8,9).random()
    }

    private fun getRandomOperator(): String {
        return listOf("*").random()
    }
}