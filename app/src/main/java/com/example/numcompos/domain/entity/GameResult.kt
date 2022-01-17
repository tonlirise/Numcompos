package com.example.numcompos.domain.entity

import java.io.Serializable

data class GameResult (
    val winner : Boolean,
    val countOfRightAnswer : Int,
    val countOfQuestions : Int,
    val gameSettings: GameSettings
) : Serializable