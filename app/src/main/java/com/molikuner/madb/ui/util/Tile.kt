package com.molikuner.madb.ui.util

import android.service.quicksettings.Tile

inline fun Tile.update(apply: Tile.() -> Unit) {
    this.apply()
    this.updateTile()
}