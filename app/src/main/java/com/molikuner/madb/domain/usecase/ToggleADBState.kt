package com.molikuner.madb.domain.usecase

import com.molikuner.madb.data.repository.MADBRepository
import com.molikuner.madb.types.ADBState
import com.molikuner.madb.types.MADBState

object ToggleADBState {
    suspend operator fun invoke(): ADBState {
        val begin = GetADBState()
        if (begin is ADBState.IsUSBADB) {
            MADBRepository.setState(MADBState(
                    MADBState.PORT_DEFAULT
            ))
        } else {
            MADBRepository.setState(MADBState(
                    MADBState.PORT_DISABLED
            ))
        }
        return GetADBState()
    }
}