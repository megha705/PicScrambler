package vardansharma.me.picscrambler.ui

import android.arch.lifecycle.LifecycleActivity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import vardansharma.me.picscrambler.R

class MainActivity : LifecycleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
