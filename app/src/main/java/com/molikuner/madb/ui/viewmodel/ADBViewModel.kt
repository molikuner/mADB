package com.molikuner.madb.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.molikuner.madb.domain.usecase.GetADBState
import com.molikuner.madb.domain.usecase.ToggleADBState
import com.molikuner.madb.types.ADBState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ADBViewModel {
    private val parentJob = Job()
    private val ioScope = CoroutineScope(Dispatchers.Main + this.parentJob)
    private var updateStateJob: Job? = null
    private val _state = MutableLiveData<ADBState>()
    val state: LiveData<ADBState>
        get() = this._state

    init {
        this.update()
    }

    fun onCleared() {
        this.parentJob.cancel()
    }

    fun update() {
        if (this.updateStateJob?.isActive == true) return

        updateStateJob = this.launchUpdate()
    }

    fun toggleState() {
        if (this.updateStateJob?.isActive == true) return

        updateStateJob = this.launchToggle()
    }

    private fun launchToggle() = this.ioScope.launch(Dispatchers.IO) {
        showRequesting()

        val result = runCatching { ToggleADBState() }

        showResult(result)
    }

    private fun launchUpdate() = this.ioScope.launch(Dispatchers.IO) {
        showRequesting()

        val result = runCatching { GetADBState() }

        showResult(result)
    }

    private suspend fun showResult(adb: Result<ADBState>) {
        withContext(Dispatchers.Main) {
            adb.getOrNull()?.also {
                emitUiState(adb = it)
            }
            adb.exceptionOrNull()?.let {
                Log.d("ADBViewModel: Exception", it.message)
                it.printStackTrace()
            }
        }
    }

    private suspend fun showRequesting() {
        withContext(Dispatchers.Main) { emitUiState() }
    }

    private fun emitUiState(adb: ADBState? = null) {
        CoroutineScope(Dispatchers.Main).launch {
            this@ADBViewModel._state.value = adb
        }
    }
}