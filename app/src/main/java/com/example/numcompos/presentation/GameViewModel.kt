package com.example.numcompos.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.numcompos.R
import com.example.numcompos.data.GameRepositoryImpl
import com.example.numcompos.domain.entity.GameResult
import com.example.numcompos.domain.entity.GameSettings
import com.example.numcompos.domain.entity.Level
import com.example.numcompos.domain.entity.Question
import com.example.numcompos.domain.usecases.GenerateQuestionUseCase
import com.example.numcompos.domain.usecases.GetGameSettingsUseCase

class GameViewModel(
    private val application: Application,
    private val gameLevel: Level
) : ViewModel() {

    private lateinit var gameSettings: GameSettings
    private var gameTimer: CountDownTimer? = null

    val gameRepository = GameRepositoryImpl
    val generateQuestionUseCase = GenerateQuestionUseCase(gameRepository)
    val getGameSettingsUseCase = GetGameSettingsUseCase(gameRepository)

    private val _gameStrTimer = MutableLiveData<String>()
    val gameStrTimer: LiveData<String>
        get() = _gameStrTimer

    private val _currQuestion = MutableLiveData<Question>()
    val currQuestion: LiveData<Question>
        get() = _currQuestion

    private val _percentOfRightAnswer = MutableLiveData<Int>()
    val percentOfRightAnswer: LiveData<Int>
        get() = _percentOfRightAnswer

    private val _answersProgress = MutableLiveData<String>()
    val answersProgress: LiveData<String>
        get() = _answersProgress

    private val _enoughContOfRightAsnswers = MutableLiveData<Boolean>()
    val enoughContOfRightAsnswers: LiveData<Boolean>
        get() = _enoughContOfRightAsnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers: LiveData<Boolean>
        get() = _enoughPercentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private var countRightAnswers = 0
    private var countQuestions = 0

    init {
        startGame()
    }

    private fun startGame() {
        initGameParams()
        startTimer()
        generateNewQuestion()
        updateProgress()
    }

    private fun initGameParams() {
        gameSettings = getGameSettingsUseCase(gameLevel)
        _minPercent.value = gameSettings.minPercentOfRightAnswer
    }

    private fun startTimer() {
        gameTimer = object :
            CountDownTimer(gameSettings.gameTimeInSeconds * MILLISEC_IN_SEC, MILLISEC_IN_SEC) {
            override fun onTick(p0: Long) {
                _gameStrTimer.value = timerTickToFormatedStr(p0)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        gameTimer?.start()
    }

    private fun timerTickToFormatedStr(millsec: Long): String {
        val allSeconds = millsec / MILLISEC_IN_SEC

        val minutes = allSeconds / SEC_IN_MIN
        val seconds = allSeconds - minutes * SEC_IN_MIN

        return String.format("%02d:%02d", minutes, seconds)
    }

    fun generateNewQuestion() {
        _currQuestion.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    fun choseAnswer(answer: Int) {
        checkAnswer(answer)
        updateProgress()
        generateNewQuestion()
    }

    private fun checkAnswer(answer: Int) {
        val rightAnswer = _currQuestion.value?.rightAnswer
        if (answer == rightAnswer) {
            countRightAnswers++
        }
        countQuestions++
    }

    private fun updateProgress() {
        val percent = calPercentOfRightAnswers()
        _percentOfRightAnswer.value = percent
        _answersProgress.value = String.format(
            application.resources.getString(R.string.progress_answers),
            countRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughContOfRightAsnswers.value = countRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercentOfRightAnswers.value = percent >= gameSettings.minPercentOfRightAnswer
    }

    private fun calPercentOfRightAnswers(): Int {
        if (countQuestions == 0) {
            return 0
        }
        return ((countRightAnswers / countQuestions.toDouble()) * 100).toInt()
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            _enoughContOfRightAsnswers.value == true
                    && _enoughPercentOfRightAnswers.value == true,
            countRightAnswers,
            countQuestions,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        gameTimer?.cancel()

    }

    companion object {
        private const val MILLISEC_IN_SEC = 1000L
        private const val SEC_IN_MIN = 60
    }

}