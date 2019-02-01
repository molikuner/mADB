package com.molikuner.madb.data.local

import java.net.InetAddress

object IPSource {
    fun getLocalIpAddress(): InetAddress {
        return InetAddress.getLocalHost()
    }
}