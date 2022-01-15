package com.example.numcompos.domain.repository

import com.example.numcompos.domain.entity.GameSettings
import com.example.numcompos.domain.entity.Level
import com.example.numcompos.domain.entity.Question

interface GameRepository {

    fun generateQuestion(maxSumValue : Int, countOfOption : Int) : Question

    fun getGameSettings(level : Level) : GameSettings
}