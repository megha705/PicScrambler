package vardansharma.me.picscrambler.ui

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.example.vardansharma.simplesttodoappever.utils.dpToPixel
import com.example.vardansharma.simplesttodoappever.utils.inflate
import com.example.vardansharma.simplesttodoappever.utils.loadImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_photos.view.*
import vardansharma.me.picscrambler.R
import vardansharma.me.picscrambler.data.Photo
import vardansharma.me.picscrambler.util.GridSpacingItemDecoration


class MainActivity : LifecycleActivity() {
    private val SPAN_COUNT = 3
    private lateinit var gameViewModel: GameViewModel
    private lateinit var photoAdapter: PhotoAdapter
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        setContentView(R.layout.activity_main)

        setUpRecyclerView()

        getPhotos()

    }

    private fun getPhotos() {
        gameViewModel.getPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { photos ->
                    photoAdapter.addPhotos(photos)
                    startTimer()
                }
    }

    private fun startTimer() {
        gameViewModel.counter()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    txt_timer.visibility = VISIBLE
                    txt_timer.text = it.toString()
                }
                        , { Log.e(TAG, it.message) },
                        {
                            txt_timer.visibility = GONE

                        })
    }


    private fun setUpRecyclerView() {
        rv_photos.layoutManager = GridLayoutManager(this, SPAN_COUNT)
        photoAdapter = PhotoAdapter()
        rv_photos.adapter = photoAdapter
        rv_photos.addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, dpToPixel(8), true))
    }

    class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotoVH>() {
        private var photos: List<Photo>? = null

        override fun onBindViewHolder(holder: PhotoVH?, position: Int) {
            holder?.bind(photos!![position])//this cannot be null because of check  in getItemCount()
        }

        override fun getItemCount(): Int {
            when (photos) {
                null -> return 0
                else -> return (photos as List<Photo>).size
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PhotoVH {
            return PhotoVH(parent?.inflate(R.layout.item_photos))
        }


        class PhotoVH(itemView: View?) : RecyclerView.ViewHolder(itemView) {
            fun bind(photo: Photo) {
                itemView.img_photos.loadImage(photo.url!!)//let's kill the game if the url is null no point going further
            }
        }

        fun addPhotos(photos: List<Photo>) {
            this.photos = photos
            notifyDataSetChanged()
        }
    }
}
