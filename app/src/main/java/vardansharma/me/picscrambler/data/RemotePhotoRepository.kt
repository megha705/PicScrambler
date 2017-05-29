package vardansharma.me.picscrambler.data

import io.reactivex.Observable

open class RemotePhotoRepository(private val flickerApi: FlickerService) : PhotoRepository {
    override fun getPhotos(): Observable<List<Photo>> {
        return flickerApi.photos
                .flatMap({ photoWrapper -> Observable.fromIterable(photoWrapper.items) })
                .map { item -> Photo(item.media!!.m) }
                .take(9)
                .toList()
                .toObservable()
    }
}

interface PhotoRepository {
    fun getPhotos(): Observable<List<Photo>>
}