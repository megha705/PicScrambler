package vardansharma.me.picscrambler

import dagger.Component
import vardansharma.me.picscrambler.data.DataModule
import javax.inject.Singleton


@Component(
        modules = arrayOf(
                AndroidModule::class,
                DataModule::class
        )
)
@Singleton
interface AppComponent {
    fun inject(into: CounterViewModel)
}

class CounterViewModel {

}
