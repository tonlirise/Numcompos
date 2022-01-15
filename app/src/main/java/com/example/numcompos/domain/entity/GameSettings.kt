package com.example.numcompos.domain.entity

data class GameSettings (
    val maxSumValue : Int,
    val minCountOfRightAnswers : Int,
    val minPercentOfRightAnswer : Int,
    val gameTimeInSeconds : Int
)