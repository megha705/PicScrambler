package vardansharma.me.picscrambler

import android.app.Application
import com.facebook.stetho.Stetho
import timber.log.Timber
import vardansharma.me.picscrambler.di.AndroidModule
import vardansharma.me.picscrambler.di.AppComponent
import vardansharma.me.picscrambler.di.DaggerAppComponent

class PicScramblerApp : Application() {

    val appComponent: AppComponent = DaggerAppComponent.builder()
            .androidModule(AndroidModule(this))
            .build()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        } else {
            // send to crashlytics or something similar
        }
    }
}