package vardansharma.me.picscrambler.ui.game

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers.io
import timber.log.Timber
import vardansharma.me.picscrambler.base.BasePresenter
import vardansharma.me.picscrambler.data.Photo
import vardansharma.me.picscrambler.data.PhotoRepository
import vardansharma.me.picscrambler.util.plus
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GamePresenter @Inject constructor(val photoRepository: PhotoRepository, val gameView: GameView) : BasePresenter {

    companion object {
        private val UPDATE_TIMER_INTERVAL: Long = 1
        private val PREVIEW_SHOWN_DURATION: Long = 15
        private val MAX_ITEMS: Int = 9
    }

    private var disposable: CompositeDisposable = CompositeDisposable()

    private lateinit var originalPhotos: List<Photo>
    private lateinit var shuffledPhotos: List<Photo>

    private var gameStarted: Boolean = false
    private var gameIndex = 0

    override fun onBind() {
        gameView.showLoading()
        disposable += photoRepository.getPhotos()
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe({ photos: List<Photo>? ->
                    when {
                        photos != null -> {
                            this.originalPhotos = photos
                            gameView.showPhotos(photos)
                            shuffledPhotos = getShuffledPhotos(photos)
                        }
                    }
                    gameView.hideLoading()
                }, {
                    gameView.hideLoading()
                    gameView.showError()
                    Timber.e(it.message)
                })
    }

    private fun getShuffledPhotos(photos: List<Photo>): List<Photo> {
        val shuffledList: MutableList<Photo> = mutableListOf()
        photos.mapTo(shuffledList) { it.copy() }
        Collections.shuffle(shuffledList)
        return shuffledList
    }

    private fun startTimer() {
        disposable += Observable.interval(1, UPDATE_TIMER_INTERVAL, TimeUnit.SECONDS)
                .map({PREVIEW_SHOWN_DURATION - it })// we want seq to start from 1 instead of 0
                .subscribeOn(io())
                .observeOn(mainThread())
                .take(PREVIEW_SHOWN_DURATION)
                .subscribe({ gameView.showTimerText(it.toString()) }, { Timber.e(it.message) },
                        {
                            gameView.hideTimerText()
                            originalPhotos.forEach { it.hidePhoto = true }// I know it's not null
                            gameView.updateAllPhotos()
                            showNextImage()
                            gameStarted = true
                        })

    }

    private fun showNextImage() {
        gameView.showPreviewImage(shuffledPhotos[gameIndex])
    }

    override fun onUnbind() {
        disposable.clear()
    }


    fun onItemClicked(position: Int) {
        if (!gameStarted) {
            timber.log.Timber.e("Game not started why are you clicking dude???")
            return
        }
        val guessedCorrectly = shuffledPhotos[gameIndex].url == originalPhotos[position].url
        if (guessedCorrectly) {
            gameIndex++
            val lastMoveInGame = gameIndex == MAX_ITEMS
            if (lastMoveInGame) {
                handleLastMove(position)
                return
            } else {
                showNextImage()
                originalPhotos[position].hidePhoto = false
                gameView.updatePhoto(position)
            }
        } else gameView.showWrongSelectedMsg()
    }

    private fun handleLastMove(position: Int) {
        gameView.showGameOverMsg()
        originalPhotos[position].hidePhoto = false
        gameView.updatePhoto(position)
        //finish the activity after 3 seconds
        disposable += Flowable.interval(0, 2, TimeUnit.SECONDS)
                .take(1)
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe({ }, {}, { gameView.finishGame() })
    }

    fun allImagesLoaded() {
        // if already started then ignore this
        if (gameStarted) {
            return
        }
        startTimer()
    }
}