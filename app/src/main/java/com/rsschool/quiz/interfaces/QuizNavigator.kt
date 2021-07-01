package com.rsschool.quiz.interfaces

interface QuizNavigator {
    fun openQuiz()
    fun goToPreviousQuestion()
    fun goToNextQuestion()
    fun goToResults()
    fun exitApp()
}