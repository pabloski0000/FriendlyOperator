package application

import domain.TimeBeforeOperationGetsSolved
import domain.Time

/**
 * This component starts a new thread, different from the client's one
 */
class Timer() {
    private var newThread: Thread? = null
    private var startTime = Time.fromMilliseconds(0)
    private var startTime2 = 0L
    private var timeLimitElapsed = false

    fun start(timeLimit: Time) {
        timeLimitElapsed = false
        startTime = Time.fromMilliseconds(System.currentTimeMillis())
        val alarm = getAlarm(timeLimit)
        newThread = Thread(alarm)
        newThread!!.start()
    }

    fun start2(timeLimit: TimeBeforeOperationGetsSolved) {
        timeLimitElapsed = false
        startTime2 = System.currentTimeMillis()
        val alarm = getAlarm2(timeLimit)
        newThread = Thread(alarm)
        newThread!!.start()
    }

    fun timeLimitHasElapsed(): Boolean {
        return timeLimitElapsed
    }

    fun stop() {
        newThread?.interrupt()
    }

    private fun getAlarm(timeLimit: Time): Runnable {
        return Runnable {
            do {
                if (Thread.interrupted()) {
                    return@Runnable
                }
                if (System.currentTimeMillis() - startTime.toMilliseconds() > timeLimit.toMilliseconds()) {
                    notifyThatTimesUp()
                    return@Runnable
                }
            } while (true)
        }
    }

    private fun getAlarm2(timeLimit: TimeBeforeOperationGetsSolved): Runnable {
        return Runnable {
            do {
                if (Thread.interrupted()) {
                    return@Runnable
                }
                if (System.currentTimeMillis() - startTime2 > timeLimit.toDouble()) {
                    notifyThatTimesUp()
                    return@Runnable
                }
            } while (true)
        }
    }

    private fun notifyThatTimesUp() {
        timeLimitElapsed = true
    }
}