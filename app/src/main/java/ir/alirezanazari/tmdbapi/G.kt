package ir.alirezanazari.tmdbapi

import android.app.Application
import ir.alirezanazari.tmdbapi.internal.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class G: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@G)
            modules(appModule)
        }
    }
}