package vardansharma.me.picscrambler.di

import android.content.Context
import vardansharma.me.picscrambler.data.DataModule
import vardansharma.me.picscrambler.data.RemotePhotoRepository


@dagger.Component(
        modules = arrayOf(
                AndroidModule::class,
                DataModule::class
        )
)
@javax.inject.Singleton
interface AppComponent {
    fun provideRepository(): RemotePhotoRepository

    fun provideContext(): Context

}
