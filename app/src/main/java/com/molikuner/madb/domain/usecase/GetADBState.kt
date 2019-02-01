package com.molikuner.madb.domain.usecase

import com.molikuner.madb.data.repository.IPRepository
import com.molikuner.madb.data.repository.MADBRepository
import com.molikuner.madb.types.ADBState
import com.molikuner.madb.types.MADBState

object GetADBState {
    suspend operator fun invoke(): ADBState {
        val madb: MADBState = MADBRepository.getState()
        return if (madb.enabled) ADBState.IsMADB(
                port = madb.port,
                ip = IPRepository.getIP()
        ) else ADBState.IsUSBADB
    }
}