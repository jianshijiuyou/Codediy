package info.jiuyou.codediy_sdk.api.base.callback

import info.jiuyou.codediy_sdk.api.base.BaseEvent
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/7/18 0018  9:21
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
open class BaseCallback<T>(val event: BaseEvent<T>) : Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        EventBus.getDefault().post(event.apply {
            code = response.code()
            data = if (response.isSuccessful) response.body() else null
        })
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        EventBus.getDefault().post(event.apply {
            code = -1
            data = null
        })
    }

}