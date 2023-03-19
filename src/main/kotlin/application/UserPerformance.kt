package application

class UserPerformance() {
    private var correctAnswers = 0
    private var wrongAnswers = 0
    private var outOfTimeAnswers = 0

    fun getPercentageOfCorrectAnswers(numberOfOperations: Int): Double {
        return correctAnswers.toDouble() / numberOfOperations * 100
    }

    fun getPercentageOfWrongAnswers(numberOfOperations: Int): Double {
        return wrongAnswers.toDouble() / numberOfOperations * 100
    }

    fun getPercentageOfOutOfTimeAnswers(numberOfOperations: Int): Double {
        return outOfTimeAnswers.toDouble() / numberOfOperations * 100
    }

    fun addOneCorrectAnswer() {
        ++correctAnswers
    }

    fun addOneWrongAnswer() {
        ++wrongAnswers
    }

    fun addOneOutOfTimeAnswer() {
        ++outOfTimeAnswers
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserPerformance

        if (correctAnswers != other.correctAnswers) return false
        if (wrongAnswers != other.wrongAnswers) return false
        if (outOfTimeAnswers != other.outOfTimeAnswers) return false

        return true
    }

    override fun hashCode(): Int {
        var result = correctAnswers
        result = 31 * result + wrongAnswers
        result = 31 * result + outOfTimeAnswers
        return result
    }


}