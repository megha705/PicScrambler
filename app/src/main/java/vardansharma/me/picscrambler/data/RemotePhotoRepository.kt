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

class InMemoryPhotoRepository : PhotoRepository {
    override fun getPhotos(): Observable<List<Photo>> {
        TODO("For now we are not implementing this")
    }

}
// there can be other version such as from disk or from a database or from a file system anything
/***
 * This class will have the responsibility to call the approriate repository remote or InMemory or
 * any other
 * */
class PhotoRepositoryManager(val inMemoryRepo: InMemoryPhotoRepository,
                             val remoteRepo: RemotePhotoRepository) : PhotoRepository {
    override fun getPhotos(): Observable<List<Photo>> {
        return remoteRepo.getPhotos()
    }
}


interface PhotoRepository {
    fun getPhotos(): Observable<List<Photo>>
}