package cn.uvuejs.kuxPlus

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.dcloud.uniapp.UniAppActivity

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        findViewById<View>(R.id.btn_goto).setOnClickListener {
            startActivity(Intent(this@MainActivity, UniAppActivity::class.java))
        }
    }
}