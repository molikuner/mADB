package com.molikuner.madb.ui

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.molikuner.madb.R
import com.molikuner.madb.types.ADBState
import com.molikuner.madb.ui.util.update
import com.molikuner.madb.ui.viewmodel.ADBViewModel

class TestTile : TileService() {
    private val viewModel: ADBViewModel by lazy {
        ADBViewModel()
    }

    override fun onClick() {
        this.viewModel.toggleState()
    }

    private fun onStateChange(adbState: ADBState?) {
        this.qsTile.update {
            when (adbState) {
                is ADBState.IsMADB -> {
                    state = Tile.STATE_ACTIVE
                    label = "${adbState.ip}:${adbState.port}"
                }
                is ADBState.IsUSBADB -> {
                    state = Tile.STATE_INACTIVE
                    label = getString(R.string.app_name)
                }
                else -> state = Tile.STATE_UNAVAILABLE
            }
        }
    }

    override fun onStartListening() {
        this.onStateChange(this.viewModel.state.value)
        this.viewModel.state.observeForever(::onStateChange)
        this.viewModel.update()
    }

    override fun onStopListening() {
        this.viewModel.state.removeObserver(::onStateChange)
    }

    override fun onDestroy() {
        this.viewModel.onCleared()
    }
}
