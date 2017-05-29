package vardansharma.me.picscrambler.ui.game

interface GameView : vardansharma.me.picscrambler.base.BaseView {

    fun showPhotos(photos: List<vardansharma.me.picscrambler.data.Photo>)

    fun updatePhoto(position: Int)

    fun showTimerText(time: String)

    fun hideTimerText()

    fun updateAllPhotos()

    fun showPreviewImage(get: vardansharma.me.picscrambler.data.Photo)

    fun showWrongSelectedMsg()

    fun showGameOverMsg()

    fun finishGame()

    fun showError()
}