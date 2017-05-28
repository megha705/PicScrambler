package vardansharma.me.picscrambler

import android.app.Application
import timber.log.Timber

class PicScramblerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }else{
            // send to crashlytics or something similar
        }
    }
}