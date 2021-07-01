package com.rsschool.quiz.data

object ContentManager {
    val questions = listOf(
        Question(
            1,
            "What is the smallest planet in our solar system?",
            arrayOf(
                "Mars",
                "Neptune",
                "Saturn",
                "Mercury",
                "Venus"
            ),
            3
        ),
        Question(
            2,
            "Name the composer behind the soundtracks of The Lion King, Inception and Pirates of the Caribbean.",
            arrayOf(
                "Howard Ashman",
                "Hanz Zimmer",
                "Scott Bradley",
                "Elton John",
                "Warren Foster"
            ),
            1
        ),
        Question(
            3,
            "What is the capital city of Australia?",
            arrayOf(
                "Melbourne",
                "Newcastle",
                "Brisbane",
                "Sydney",
                "Canberra"
            ),
            4
        ),
        Question(
            4,
            "How many notes are there in a musical scale?",
            arrayOf(
                "7",
                "5",
                "9",
                "12",
                "6"
            ),
            0
        ),
        Question(
            5,
            "What colour are most buses in London?",
            arrayOf(
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