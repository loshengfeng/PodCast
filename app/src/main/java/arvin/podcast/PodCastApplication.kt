package arvin.podcast

import android.app.Application
import arvin.podcast.di.networkModule
import arvin.podcast.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PodCastApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@PodCastApplication)
            modules(listOf(networkModule, viewModelModule))
        }
    }
}