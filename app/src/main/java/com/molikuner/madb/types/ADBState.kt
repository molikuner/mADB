package com.molikuner.madb.types

sealed class ADBState {
    data class IsMADB(
        val port: Int,
        val ip: String
    ) : ADBState()

    object IsUSBADB : ADBState()
}