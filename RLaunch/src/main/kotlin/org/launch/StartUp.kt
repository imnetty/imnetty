package org.launch

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import com.google.inject.Guice
import com.google.inject.Injector
import lombok.Getter
import org.launch.settings.Configuration
import javax.inject.Inject

class StartUp {
    companion object {
        @Getter
        lateinit var injector: Injector
        @JvmStatic
        fun main(args: Array<String>) {
            injector = Guice.createInjector(Module())
            injector.getInstance(Launcher().javaClass).start()
        }
    }
}