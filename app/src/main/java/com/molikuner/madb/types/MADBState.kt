package com.molikuner.madb.types

data class MADBState(
    val port: Int,
    val enabled: Boolean = port > 0
) {
    companion object {
        const val PORT_DISABLED = -1
        const val PORT_DEFAULT = 5555
    }
}