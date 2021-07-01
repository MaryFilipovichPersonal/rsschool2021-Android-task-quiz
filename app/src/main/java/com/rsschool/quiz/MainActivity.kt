package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.rsschool.quiz.data.AnswersManager
import com.rsschool.quiz.data.ContentManager
import com.rsschool.quiz.databinding.ActivityMainBinding
import com.rsschool.quiz.interfaces.QuizNavigator

class MainActivity : AppCompatActivity(), QuizNavigator {

    //view binding
    private lateinit var binding: ActivityMainBinding

    private var currentQuestion = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            openQuiz()
        }

    }

    override fun openQuiz() {
        clearBackStack()
        resetAnswers()
        currentQuestion = 1
        setQuestionTheme()
        val tag = getString(R.string.question_fragment, currentQuestion)
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, QuestionFragment.newInstance(currentQuestion), tag)
            .commit()
    }

    override fun goToPreviousQuestion() {
        if (currentQuestion > 1) {
            currentQuestion--
            setQuestionTheme()
            supportFragmentManager.popBackStack()
        }
    }

    override fun goToNextQuestion() {
        when (currentQuestion) {
            in 1 until ContentManager.questions.size -> {
                currentQuestion++
                setQuestionTheme()
                val tag = getString(R.string.question_fragment, currentQuestion)
                supportFragmentManager.beginTransaction()
                    .replace(
                        binding.container.id,
                        QuestionFragment.newInstance(currentQuestion),
                        tag
                    )
                    .addToBackStack(null)
                    .commit()
            }
            else -> goToResults()
        }
    }

    override fun goToResults() {
        clearBackStack()
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, ResultFragment.newInstance())
            .commit()
    }

    override fun exitApp() {
        finish()
    }

    private fun setQuestionTheme() {
        setTheme(
            when (currentQuestion) {
                1 -> R.style.Theme_Quiz_First.also {
                    window.statusBarColor =
                        ContextCompat.getColor(this, R.color.deep_orange_100_dark)
                }
                2 -> R.style.Theme_Quiz_Second.also {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_100_dark)
                }
                3 -> R.style.Theme_Quiz_Third.also {
                    window.statusBarColor =
                        ContextCompat.getColor(this, R.color.light_green_100_dark)
                }
                4 -> R.style.Theme_Quiz_Fourth.also {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.cyan_100_dark)
                }
                else -> R.style.Theme_Quiz_Fifth.also {
                    window.statusBarColor =
                        ContextCompat.getColor(this, R.color.deep_purple_100_dark)
                }
            }
        )
    }

    private fun resetAnswers() {
        AnswersManager.answerIds = IntArray(ContentManager.questions.size) { 5 }
    }

    private fun clearBackStack() {
        with(supportFragmentManager) {
            popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }
}