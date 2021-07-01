package com.rsschool.quiz.data

object QuestionsManager {
    val questions = listOf(
        Question(
            "What is the smallest planet in our solar system?",
            arrayListOf(
                "Mars",
                "Neptune",
                "Saturn",
                "Mercury",
                "Venus"
            ),
            3
        ),
        Question(
            "Name the composer behind the soundtracks of The Lion King, Inception and Pirates of the Caribbean.",
            arrayListOf(
                "Howard Ashman",
                "Hanz Zimmer",
                "Scott Bradley",
                "Elton John",
                "Warren Foster"
            ),
            1
        ),
        Question(
            "What is the capital city of Australia?",
            arrayListOf(
                "Melbourne",
                "Newcastle",
                "Brisbane",
                "Sydney",
                "Canberra"
            ),
            4
        ),
        Question(
            "How many notes are there in a musical scale?",
            arrayListOf(
                "7",
                "5",
                "9",
                "12",
                "6"
            ),
            0
        ),
        Question(
            "What colour are most buses in London?",
            arrayListOf(
                "Blue",
                "Yellow",
                "Red",
                "Orange",
                "Green"
            ),
            2
        )
    )
}