package vardansharma.me.picscrambler.ui.game

import vardansharma.me.picscrambler.data.PhotoRepositoryManager

/**
 * Created by vardansharma on 28/05/17.
 */
@dagger.Module
class GameModule(val view: GameView) {

    @dagger.Provides
    fun providesView(): GameView {
        return view
    }

    @dagger.Provides
    fun providePresenter(view: GameView, repository: PhotoRepositoryManager): GamePresenter {
        return GamePresenter(repository, view)
    }
}