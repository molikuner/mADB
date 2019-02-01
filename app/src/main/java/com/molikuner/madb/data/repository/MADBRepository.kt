package com.molikuner.madb.data.repository

import com.molikuner.madb.data.local.MADBService
import com.molikuner.madb.types.MADBState

object MADBRepository {
    suspend fun getState(): MADBState {
        return MADBService.requestState()
    }

    suspend fun setState(to: MADBState) {
        MADBService.applyState(to)
    }
}