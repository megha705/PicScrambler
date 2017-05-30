package vardansharma.me.picscrambler.ui.gameover

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game_over.*
import org.jetbrains.anko.startActivity
import vardansharma.me.picscrambler.R
import vardansharma.me.picscrambler.ui.game.GameViewActivity

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        btn_play_again.setOnClickListener {
            startActivity<GameViewActivity>()
            finish()
        }
    }
}
