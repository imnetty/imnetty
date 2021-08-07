package org.launch.settings

import com.beust.klaxon.Klaxon
import org.apache.commons.io.FileUtils
import org.launch.settings.data.UpdateType
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer

data class Configuration(
    /**
     * Launcher Configs
     */
    val serverName: String = "Server Name",
    val cacheDirectory: String = System.getProperty("user.home") + "/$serverName/",

    /**
     * Update method settings
     */
    val updateType: MutableList<UpdateType> = mutableListOf(UpdateType.CLIENT),
    val downloadSpeedLimit: Double = (FileUtils.ONE_MB * 5).toDouble(),

    /**
     * Update URLS
     */
    val clientUrl: String = "https://google.com",
    val updatePackages: String = "https://google.com",

    /**
     * Launcher Features
     */
    val showServerStats: Boolean = true,
    val showUpdates: Boolean = true,
    val showServerFeatures: Boolean = true
)