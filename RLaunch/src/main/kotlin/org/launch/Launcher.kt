package org.launch

import ch.qos.logback.core.util.FileUtil
import com.beust.klaxon.Klaxon
import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import com.google.common.util.concurrent.RateLimiter
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Provides
import lombok.Getter
import org.apache.commons.io.FileUtils
import org.launch.Utils.getLogger
import org.launch.Utils.getTimeFromMillis
import org.launch.events.LoadedObject
import org.launch.settings.Configuration
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.InputStream
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import javax.inject.Inject
import javax.inject.Singleton

class Launcher {
        @Inject
        lateinit var eventBus: EventBus
        @Inject
        lateinit var configuration: Configuration
        @Inject
        lateinit var loadingScreen: LoadingScreen
        fun start() {
            eventBus.register(this)
            eventBus.post(configuration)
        }
        @Subscribe
        fun debugConfiguration(configuration: Configuration) {
            getLogger(javaClass).info("Starting launcher with the the configuration: \n$configuration")
            getLogger(Date::class.java).info("Current date: " + getTimeFromMillis(System.currentTimeMillis()))
        }
}

class Module: AbstractModule() {
    override fun configure() {
        bind(ScheduledExecutorService::class.java).toInstance(Executors.newScheduledThreadPool(10))
        bind(LoadingScreen::class.java)
    }

    @Provides
    @Singleton
    fun provideEventBus(): EventBus? {
        return EventBus()
    }

    @Provides
    @Singleton
    fun provideConfiguration(): Configuration {
        return Klaxon().parse(Configuration::class.java.classLoader.getResourceAsStream("Settings.json"))!!
    }
}

