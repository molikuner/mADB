package com.molikuner.madb.data.local

import com.molikuner.madb.data.local.util.rrun
import com.molikuner.madb.data.local.util.run
import com.molikuner.madb.types.MADBState
import com.topjohnwu.superuser.Shell

object MADBService {
    suspend fun requestState(): MADBState {
        val stateResult = run("getprop service.adb.tcp.port")
        if (!stateResult.isSuccess)
            throw IllegalStateException("MADB State Exception")
        try {
            return MADBState(port = stateResult.out[0].toInt())
        } catch (e: NumberFormatException) {
            throw IllegalStateException("MADB State Exception")
        }
    }

    suspend fun applyState(s: MADBState) {
        if (!Shell.rootAccess()) throw IllegalStateException("root Exception")
        val applyResult = rrun("setprop service.adb.tcp.port ${s.port}", "stop adbd", "start adbd")
        if (!applyResult.isSuccess)
            throw IllegalStateException("MADB Change Exception")
    }
}