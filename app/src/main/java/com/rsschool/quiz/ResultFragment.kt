package com.rsschool.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.rsschool.quiz.data.AnswersManager
import com.rsschool.quiz.data.ContentManager
import com.rsschool.quiz.databinding.FragmentResultBinding
import com.rsschool.quiz.interfaces.QuizNavigator

class ResultFragment : Fragment() {

    //view binding
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    //navigator
    private var navigationManager: QuizNavigator? = null

    //back button handler
    private var onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            navigationManager?.openQuiz()
        }
    }

    //quiz result in percent
    private var quizResult = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is QuizNavigator) {
            navigationManager = context
        }
    }

    override fun onDetach() {
        navigationManager = null
        super.onDetach()
    }

    override fun onDestroyView() {
        onBackPressedCallback.isEnabled = false
        onBackPressedCallback.remove()
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setQuizResult()

        setListeners()
    }

    private fun setListeners() {
        setShareButtonClickListener()
        setTryAgainButtonClickListener()
        setExitButtonClickListener()
        setBackButtonHandler()
    }

    private fun setBackButtonHandler() {
        requireActivity().onBackPressedDispatcher.addCallback(
            requireActivity(),
            onBackPressedCallback
        )
    }

    private fun setShareButtonClickListener() {
        binding.frBtnShareResult.setOnClickListener {
            val report = getQuizReport()
            val sendIntent: Intent = getSendIntent(report)
            startActivity(Intent.createChooser(sendIntent, null))
        }
    }

    private fun setTryAgainButtonClickListener() {
        binding.frBtnTryAgain.setOnClickListener {
            navigationManager?.openQuiz()
        }
    }

    private fun setExitButtonClickListener() {
        binding.frBtnExit.setOnClickListener{
            navigationManager?.exitApp()
        }
    }

    private fun setQuizResult() {
        quizResult = calculateResult()
        binding.frTvQuizResult.text = getString(R.string.quiz_result, quizResult)
    }

    private fun getQuizReport(): String {
        val questionsWithAnswers = ContentManager.questions.zip(AnswersManager.answerIds.toList())
        val report = StringBuilder()
        with(report) {
            appendLine("Hello! My quiz result: $quizResult%")
            questionsWithAnswers.forEach { questionWithAnswer ->
                with(questionWithAnswer) {
                    appendLine()
                    appendLine("Question: ${first.question}")
                    appendLine()
                    appendLine("Selected answer: ${first.answers[second]}")
                }
            }
        }
        return report.toString()
    }

    private fun getSendIntent(report: String) = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, report)
        type = "text/plain"
    }

    private fun calculateResult(): Int = with(AnswersManager) {
        (answerIds.filterIndexed
        { index, answerId ->
            answerId == ContentManager.questions[index].correctAnswerId
        }.size * 100) / answerIds.size
    }

    companion object {
        @JvmStatic
        fun newInstance() = ResultFragment()
    }

}