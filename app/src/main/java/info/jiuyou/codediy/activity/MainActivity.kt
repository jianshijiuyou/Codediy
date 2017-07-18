package info.jiuyou.codediy.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import info.jiuyou.codediy.R
import org.jetbrains.anko.AnkoLogger


class MainActivity : AppCompatActivity(), AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
