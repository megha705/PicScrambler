package vardansharma.me.picscrambler.ui.game

import vardansharma.me.picscrambler.data.RemotePhotoRepository

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
    fun providePresenter(view: GameView, repository: RemotePhotoRepository): GamePresenter {
        return GamePresenter(repository, view)
    }
}