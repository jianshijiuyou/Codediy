package info.jiuyou.codediy

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import info.jiuyou.codediy_sdk.api.login.api.LoginImpl
import info.jiuyou.codediy_sdk.api.login.UpdateDevicesEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class MainActivity : AppCompatActivity(), AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
