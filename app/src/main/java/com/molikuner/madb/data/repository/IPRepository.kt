package com.molikuner.madb.data.repository

import com.molikuner.madb.data.local.IPSource

object IPRepository {
    suspend fun getIP(): String {
        return IPSource.getLocalIpAddress().hostAddress
    }
}