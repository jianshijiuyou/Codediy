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
        EventBus.getDefault().register(this)
        val uuid = LoginImpl(this@MainActivity).updateDevices()
        info("uuid:$uuid")

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: UpdateDevicesEvent) {
        info(event.uuid)
        info(event.codeDes())
        event.exist {
            info(data)
        }
    }


    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}
