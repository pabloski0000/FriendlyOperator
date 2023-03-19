package application

import domain.Time
import kotlinx.coroutines.yield

class AlertMeIfTimeLimitIsExceededUseCase {
    private val timer = Timer()

    suspend fun alert(timeLimit: Time, alertee: Alertee) {
        timer.start(timeLimit)
        while (! timer.timeLimitHasElapsed()) {
            yield()
        }
        alertee.timeLimitExceeded()
    }
}