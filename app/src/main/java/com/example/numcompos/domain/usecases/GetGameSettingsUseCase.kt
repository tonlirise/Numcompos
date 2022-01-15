package com.example.numcompos.domain.usecases

import com.example.numcompos.domain.entity.GameSettings
import com.example.numcompos.domain.entity.Level
import com.example.numcompos.domain.repository.GameRepository

class GetGameSettingsUseCase (private val gameRepository: GameRepository){
    operator fun invoke(level : Level) : GameSettings{
        return gameRepository.getGameSettings(level)
    }
}