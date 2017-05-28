package vardansharma.me.picscrambler

import dagger.Component
import vardansharma.me.picscrambler.data.DataModule
import vardansharma.me.picscrambler.ui.GameViewModel
import javax.inject.Singleton


@Component(
        modules = arrayOf(
                AndroidModule::class,
                DataModule::class
        )
)
@Singleton
interface AppComponent {
    fun inject(into: GameViewModel)
}
