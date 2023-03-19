package ui

import Calculator
import OperationAsStringAdapter
import application.*
import domain.Operation
import domain.Operand
import domain.Time
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader

class UI : Alertee {
    private val userInputReader = BufferedReader(
        InputStreamReader(System.`in`)
    )
    private val userPerformance = UserPerformance()
    private val checkSolutionUseCase = CheckSolutionUseCase()
    private val alertMeIfTimeLimitIsExceededUseCase = AlertMeIfTimeLimitIsExceededUseCase()
    private var timeLimitExceeded = false
    private val coroutineScopeOnMainThread = CoroutineScope(Dispatchers.Main)

    suspend fun run(numberOfOperations: Int) {
        val timeLimit = askUserAboutTimeLimitInSeconds()
        for (numberOfOperation in 0 until numberOfOperations) {
            val operation = Operation.Multiplication.generateRandomOne()
            print(OperationPresenter.present(operation))
            coroutineScopeOnMainThread.launch(Dispatchers.IO) {
                alertMeIfTimeLimitIsExceededUseCase.alert(timeLimit, this@UI)
            }
            val solution = operation.getResult()
            waitUntilUserRespondsOrTimeLimitIsExceeded()
            if (userHasAnswered()) {
                val userSolution = userInputReader.readLine()
                if (solutionFormatIsCorrect(userSolution) && checkSolutionUseCase.check(operation, solution)) {
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

    private fun solutionFormatIsCorrect(userSolution: String): Boolean {
        return userSolution.toDoubleOrNull() != null
    }

    private fun informUserOfTheirPerformance(userPerformance: UserPerformance, numberOfOperations: Int) {
        println("Finished. These are the results")
        println("The percentage of right answers is: ${userPerformance.getPercentageOfCorrectAnswers(numberOfOperations)}%")
        println("The percentage of wrong answers is: ${userPerformance.getPercentageOfWrongAnswers(numberOfOperations)}%")
        println("The percentage of out of time answers is: ${userPerformance.getPercentageOfOutOfTimeAnswers(numberOfOperations)}%")
    }

    private suspend fun waitUntilUserRespondsOrTimeLimitIsExceeded() {
        while (! (userInputReader.ready() ||  timeLimitExceeded)) {
            yield()
        }
        timeLimitExceeded = false
    }

    private fun userHasAnswered(): Boolean {
        return userInputReader.ready()
    }

    override fun timeLimitExceeded() {
        timeLimitExceeded = true
    }
}

