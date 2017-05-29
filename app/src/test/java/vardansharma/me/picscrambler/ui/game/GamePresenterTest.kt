package vardansharma.me.picscrambler.ui.game

import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import vardansharma.me.picscrambler.data.Photo
import vardansharma.me.picscrambler.data.PhotoRepository

/**
 * Created by vardansharma on 29/05/17.
 */
class GamePresenterTest {
    @Mock
    lateinit var view: GameView

    @Mock
    lateinit var photosRepo: PhotoRepository


    lateinit var presenter: GamePresenter


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = GamePresenter(gameView = view, photoRepository = photosRepo)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun shouldHideLoadingOnError() {
        //when

        `when`(photosRepo.getPhotos()).thenReturn(Observable.error<List<Photo>>(Exception()))

        //then
        presenter.onBind()

        //assert

        verify(view).hideLoading()
    }

    @Test
    fun shouldHideLoadingOnSuccess() {
        `when`(photosRepo.getPhotos()).thenReturn(Observable.just(listOf()))

        presenter.onBind()

        verify(view).hideLoading()
    }

    @Test
    fun shouldShowLoadingBeforeMakingRequest() {
        `when`(photosRepo.getPhotos()).thenReturn(Observable.just(listOf()))

        presenter.onBind()

        verify(view).showLoading()
    }

    @Test
    fun shouldShowCorrectDataInViewOnSuccess() {
        `when`(photosRepo.getPhotos()).thenReturn(Observable.just(PhotoFakeData.list))

        presenter.onBind()

        verify(view).showPhotos(PhotoFakeData.list)
    }

    class PhotoFakeData {
        companion object {
            val PHOTO_1: Photo = Photo("url1")
            val PHOTO_2: Photo = Photo("url2")
            val PHOTO_3: Photo = Photo("url3")
            val PHOTO_4: Photo = Photo("url4")
            val PHOTO_5: Photo = Photo("url5")
            val PHOTO_6: Photo = Photo("url6")
            val PHOTO_7: Photo = Photo("url7")
            val PHOTO_8: Photo = Photo("url8")

            val list = listOf<Photo>(PHOTO_1, PHOTO_2, PHOTO_3, PHOTO_4, PHOTO_5, PHOTO_6, PHOTO_7, PHOTO_8)
        }
    }


}