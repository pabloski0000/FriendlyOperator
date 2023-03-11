package ui

import Calculator
import OperationAsStringAdapter
import domain.Timer
import java.io.BufferedReader
import java.io.InputStreamReader

class UI {
    private val calculator = Calculator()
    private val stringOperationAdapter = OperationAsStringAdapter()
    private val userInputReader = BufferedReader(
        InputStreamReader(System.`in`)
    )

    fun run(numberOfOperations: Int) {
        val userPerformance = UserPerformance()
        val timer = Timer()
        val randomOperationProducer = RandomOperationProducer()
        val timeLimit = askUserAboutTimeLimitInSeconds()
        for (numberOfOperation in 0 until numberOfOperations) {
            val operation = randomOperationProducer.getRandomOperation()
            showOperation(operation)
            timer.start(timeLimit)
            while (! timer.timeLimitHasElapsed()) {
                if (userHasAnswered()) {
                    timer.stop()
                    break
                }
            }
            val calculatorSolution = calculateSolution(operation)
            if (userHasAnswered()) {
                val userSolution = readUserSolution()
                if (solutionFormatIsCorrect(userSolution) && solutionIsCorrect(userSolution, calculatorSolution)) {
                    userPerformance.addOneCorrectAnswer()
                    print(" Correct")
                } else {
                    userPerformance.addOneWrongAnswer()
                    print("\b Wrong")
                }
            } else {
                userPerformance.addOneOutOfTimeAnswer()
                print(" Out Of Time")
            }
            print("\n")
        }
        informUserOfTheirPerformance(userPerformance, numberOfOperations)
    }

    private fun askUserAboutTimeLimitInSeconds(): Time {
        println("Tell me how much time - in seconds - you want me to last before I solve the operations: ")
        return Time.fromSeconds(readln().toDouble())
    }

    private fun showOperation(operation: String) {
        print("$operation = ")
    }

    private fun solutionFormatIsCorrect(userSolution: String): Boolean {
        return userSolution.toDoubleOrNull() != null
    }

    private fun solutionIsCorrect(userSolution: String, calculatorSolution: Double): Boolean {
        return userSolution.toDouble() == calculatorSolution
    }

    private fun informUserOfTheirPerformance(userPerformance: UserPerformance, numberOfOperations: Int) {
        println("Finished. These are the results")
        println("The percentage of right answers is: ${userPerformance.getPercentageOfCorrectAnswers(numberOfOperations)}%")
        println("The percentage of wrong answers is: ${userPerformance.getPercentageOfWrongAnswers(numberOfOperations)}%")
        println("The percentage of out of time answers is: ${userPerformance.getPercentageOfOutOfTimeAnswers(numberOfOperations)}%")
    }

    private fun calculateSolution(operation: String): Double {
        return calculator.calculate(
            stringOperationAdapter.adaptOperation(operation)
        )
    }

    private fun userHasAnswered(): Boolean {
        return userInputReader.ready()
    }

    private fun readUserSolution(): String {
        return userInputReader.readLine()
    }
}

