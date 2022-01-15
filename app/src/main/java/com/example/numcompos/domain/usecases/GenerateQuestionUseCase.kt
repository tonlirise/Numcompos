package com.example.numcompos.domain.usecases

import com.example.numcompos.domain.entity.Question
import com.example.numcompos.domain.repository.GameRepository

class GenerateQuestionUseCase(private val gameRepository: GameRepository) {
    operator fun invoke(maxSumValue : Int, countOfOption : Int) : Question{
        return gameRepository.generateQuestion(maxSumValue, COUNT_OF_OPTION)
    }

    companion object {
        private const val COUNT_OF_OPTION = 6
    }
}