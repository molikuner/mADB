package com.molikuner.madb.data.local.util

import com.topjohnwu.superuser.Shell
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun Shell.Job.run(): Shell.Result = suspendCoroutine {
    this.submit { result ->
        it.resume(result)
    }
}

suspend fun run(vararg commands: String) = Shell.sh(*commands).run()

suspend fun rrun(vararg commands: String) = Shell.su(*commands).run()
