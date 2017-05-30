package vardansharma.me.picscrambler.ui.game

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.example.vardansharma.simplesttodoappever.utils.*
import kotlinx.android.synthetic.main.activity_game.*
import org.jetbrains.anko.design.snackbar
import vardansharma.me.picscrambler.PicScramblerApp
import vardansharma.me.picscrambler.R
import vardansharma.me.picscrambler.data.Photo
import vardansharma.me.picscrambler.util.GridSpacingItemDecoration


class GameViewActivity : AppCompatActivity(), GameView, PhotoAdapter.PhotoClickListener {
    companion object {
        private val SPAN_COUNT = 3
    }

    private lateinit var photoAdapter: PhotoAdapter

    @javax.inject.Inject
    lateinit var presenter: GamePresenter


    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        initDI()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        setUpRecyclerView()
        presenter.onBind()
    }

    private fun initDI() {
        val appComponent = (application as PicScramblerApp).appComponent
        DaggerGameComponent.builder()
                .gameModule(GameModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this)
    }


    override fun showLoading() {
        game_progress_bar.show()
    }

    override fun hideLoading() {
        game_progress_bar.gone()
    }

    override fun showPhotos(photos: List<Photo>) {
        photoAdapter.addPhotos(photos)
    }

    override fun updatePhoto(position: Int) {
        photoAdapter.notifyItemChanged(position)
    }

    override fun showTimerText(time: String) {
        txt_timer.show()
        txt_timer.text = time
    }

    override fun hideTimerText() {
        txt_timer.gone()
    }

    override fun updateAllPhotos() {
        photoAdapter.notifyDataSetChanged()
    }

    override fun showPreviewImage(get: Photo) {
        img_preview.show()
        img_preview.loadImage(get.url!!)
    }

    override fun onItemClick(position: Int) {
        presenter.onItemClicked(position)
    }

    override fun showGameOverMsg() {
        toast(getString(vardansharma.me.picscrambler.R.string.game_over))
    }

    override fun finishGame() {
        finish()
    }

    override fun showWrongSelectedMsg() {
        snackbar(rv_photos, getString(R.string.wrong_guess))
    }

    override fun allImagesLoaded() {
        presenter.allImagesLoaded()
    }

    override fun showError() {
        snackbar(rv_photos, getString(R.string.no_internet_connection))
    }

    private fun setUpRecyclerView() {
        rv_photos.layoutManager = GridLayoutManager(this, SPAN_COUNT)
        photoAdapter = PhotoAdapter(this)
        rv_photos.adapter = photoAdapter
        rv_photos.addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, dpToPixel(8), true))
    }

}
