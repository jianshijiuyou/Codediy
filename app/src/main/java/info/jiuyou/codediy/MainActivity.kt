package info.jiuyou.codediy

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import info.jiuyou.codediy_sdk.api.base.event.BaseEvent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val b = BaseEvent <String>("")
        println(b.codeDes)
        b.code=200
        println(b.codeDes)
        b.code=404
        println(b.codeDes)
        b.code=401
        println(b.codeDes)
        b.code=123
        println(b.codeDes)
    }
}
