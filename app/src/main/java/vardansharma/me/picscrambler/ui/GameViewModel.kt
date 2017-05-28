package vardansharma.me.picscrambler.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import io.reactivex.Flowable
import io.reactivex.Single
import vardansharma.me.picscrambler.PicScramblerApp
import vardansharma.me.picscrambler.data.Photo
import vardansharma.me.picscrambler.data.PhotoRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GameViewModel(application: Application?) : AndroidViewModel(application) {
    @Inject lateinit var photos: PhotoRepository //photo repo through dagger injection

    init {
        (application as PicScramblerApp).appComponent.inject(this)
    }


    fun positionClicked(position: Int) {
        TODO("not yet implemented")
    }

    fun counter(): Flowable<Long> {
        return Flowable.interval(1, 1, TimeUnit.SECONDS)
                .take(15)
                .map({ it + 1 })// it starts from 0 we want it to start from 1

    }

    fun getPhotos(): Single<List<Photo>> {
        dataArrived = true
        return photos.getPhotos()
    }

    fun isGameStarted() {

    }

    var dataArrived: Boolean = false
}