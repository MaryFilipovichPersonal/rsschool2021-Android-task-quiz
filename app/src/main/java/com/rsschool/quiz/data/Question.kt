package com.rsschool.quiz.data

data class Question(
    val question: String,
    val answers: List<String>,
    val correctAnswerId: Int
)