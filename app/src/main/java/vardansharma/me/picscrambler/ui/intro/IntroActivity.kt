package vardansharma.me.picscrambler.ui.intro

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_intro.*
import org.jetbrains.anko.startActivity
import vardansharma.me.picscrambler.R
import vardansharma.me.picscrambler.ui.game.GameViewActivity

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        btn_play.setOnClickListener {
            startActivity<GameViewActivity>()
            finish()
        }
    }
}
