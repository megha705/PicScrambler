package vardansharma.me.picscrambler.data

import io.reactivex.Flowable
import io.reactivex.Single

class PhotoRepository(private val flickerApi: FlickerService) {
    fun getPhotos(): Single<List<Photo>> {
        // This is not an optimal implementation, we'll fix it below

        // I know this won't be null
        return flickerApi!!.photos
                .flatMap({ photoWrapper -> Flowable.fromIterable(photoWrapper.items) })
                .map { item -> Photo(item.media!!.m) }
                .take(9)
                .toList()
    }
}