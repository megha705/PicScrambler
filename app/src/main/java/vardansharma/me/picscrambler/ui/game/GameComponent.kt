package vardansharma.me.picscrambler.ui.game

import dagger.Component
import vardansharma.me.picscrambler.di.ActivityScope
import vardansharma.me.picscrambler.di.AppComponent

@Component(
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(GameModule::class)
)
@ActivityScope
interface GameComponent {
    fun inject(target: GameViewActivity)
}