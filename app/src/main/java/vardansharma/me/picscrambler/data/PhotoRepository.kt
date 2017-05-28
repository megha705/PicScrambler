package vardansharma.me.picscrambler.data

import io.reactivex.Observable

class PhotoRepository(private val flickerApi: FlickerService) {
    fun getPhotos(): Observable<Photo> {
        // This is not an optimal implementation, we'll fix it below

        // I know this won't be null
        return flickerApi!!.photos
                .flatMap({ photoWrapper -> Observable.fromIterable(photoWrapper.items) })
                .map { item -> Photo(item.media!!.m) }
    }
}