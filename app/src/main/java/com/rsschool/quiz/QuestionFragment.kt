package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.data.AnswersManager
import com.rsschool.quiz.data.ContentManager
import com.rsschool.quiz.databinding.FragmentQuestionBinding
import com.rsschool.quiz.interfaces.QuizNavigator

class QuestionFragment : Fragment() {

    //view binding
    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    //navigator
    private var navigationManager: QuizNavigator? = null

    //current question and default answer number
    private var questionNum: Int = 0
    private var answerId = 5

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
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
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getQuestionNumber()

        setUI()

        setListeners()
    }

    private fun getQuestionNumber() {
        arguments?.let { bundle ->
            questionNum = bundle.getInt(QUESTION_NUMBER)
            answerId = AnswersManager.answerIds[questionNum - 1]
        }
    }

    private fun setUI() {
        with(binding) {
            when (questionNum) {
                1 -> {
                    fqPreviousButton.visibility = View.INVISIBLE
                    fqToolbar.navigationIcon = null
                }
                5 -> fqNextButton.text = getString(R.string.submit_button)
            }
            fqToolbar.title = getString(R.string.toolbar_name, questionNum)
            with(ContentManager.questions[questionNum - 1]) {
                fqQuestion.text = question
                fqOptionOne.text = answers[0]
                fqOptionTwo.text = answers[1]
                fqOptionThree.text = answers[2]
                fqOptionFour.text = answers[3]
                fqOptionFive.text = answers[4]
                if (answerId < 5) {
                    (fqRadioGroup.getChildAt(answerId) as RadioButton).isChecked = true
                    setNextButtonEnabled()
                }
            }
        }
    }

    private fun setListeners() {
        setNextButtonClickListener()
        setPreviousButtonClickListener()
        setToolbarButtonClick()
        setRadioGroupStateChangeListener()
    }

    private fun setNextButtonClickListener() {
        binding.fqNextButton.setOnClickListener {
            if (questionNum == 5)
                navigationManager?.goToResults()
            else
                navigationManager?.goToNextQuestion()
        }

    }

    private fun setPreviousButtonClickListener() {
        binding.fqPreviousButton.setOnClickListener {
            if (questionNum > 1)
                navigationManager?.goToPreviousQuestion()
        }
    }

    private fun setToolbarButtonClick() {
        binding.fqToolbar.setNavigationOnClickListener {
            if (questionNum > 1)
                navigationManager?.goToPreviousQuestion()
        }
    }

    private fun setRadioGroupStateChangeListener() {
        with(binding.fqRadioGroup) {
            setOnCheckedChangeListener { _, checkedId ->
                val index = indexOfChild(findViewById(checkedId))
                answerId = index
                AnswersManager.answerIds[questionNum - 1] = answerId
                setNextButtonEnabled()
            }
        }
    }

    private fun setNextButtonEnabled(state: Boolean = true) {
        binding.fqNextButton.isEnabled = state
    }

    companion object {
        @JvmStatic
        fun newInstance(questionNumber: Int) = QuestionFragment().apply {
            arguments = bundleOf(
                QUESTION_NUMBER to questionNumber
            )
        }

        private const val QUESTION_NUMBER = "QUESTION_NUMBER"
    }

}