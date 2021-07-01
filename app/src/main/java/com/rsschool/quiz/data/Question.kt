package com.rsschool.quiz.data

data class Question(
    val number: Int,
    val question: String,
    val answers: Array<String>,
    val correctAnswerId: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Question

        if (number != other.number) return false
        if (question != other.question) return false
        if (!answers.contentEquals(other.answers)) return false
        if (correctAnswerId != other.correctAnswerId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = number
        result = 31 * result + question.hashCode()
        result = 31 * result + answers.contentHashCode()
        result = 31 * result + correctAnswerId
        return result
    }
}