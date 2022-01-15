package com.example.numcompos.domain.entity

data class GameResult (
    val winner : Boolean,
    val countOfRightAnswer : Int,
    val countOfQuestions : Int,
    val gameSettings: GameSettings
)