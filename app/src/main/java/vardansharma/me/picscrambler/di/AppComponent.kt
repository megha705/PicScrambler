package vardansharma.me.picscrambler.di

import android.content.Context
import vardansharma.me.picscrambler.data.DataModule
import vardansharma.me.picscrambler.data.PhotoRepositoryManager


@dagger.Component(
        modules = arrayOf(
                AndroidModule::class,
                DataModule::class
        )
)
@javax.inject.Singleton
interface AppComponent {
    fun provideRepository(): PhotoRepositoryManager

    fun provideContext(): Context

}
