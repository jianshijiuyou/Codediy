package info.jiuyou.codediy.base.app

import android.app.Application
import com.gcssloop.diycode_sdk.api.Diycode
import com.squareup.leakcanary.LeakCanary
import info.jiuyou.codediy.utils.Config

/**
 * ==========================================
 *
 *
 * 版   权 ：jianshijiuyou(c) 2017
 * <br></br>
 * 作   者 ：wq
 * <br></br>
 * 版   本 ：1.0
 * <br></br>
 * 创建日期 ：17-7-18  下午9:48
 * <br></br>
 * 描   述 ：
 * <br></br>
 * 修订历史 ：
 *
 * ==========================================
 */
class BaseApp : Application() {

    companion object {
        val client_id = "f31fc3b7"
        val client_secret = "7ad0caa0ef5fd55c7fe7b7c644de4cd14137f88f65d14f487f65b7d950eaaab2"
    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
        Diycode.init(this, client_id, client_secret)
        Config.init(this)
    }
}
