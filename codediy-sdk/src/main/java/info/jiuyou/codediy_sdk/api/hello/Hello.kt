package info.jiuyou.codediy_sdk.api.hello

import android.content.Context
import info.jiuyou.codediy_sdk.api.base.BaseImpl
import info.jiuyou.codediy_sdk.api.base.callback.BaseCallback
import info.jiuyou.codediy_sdk.api.base.BaseEvent
import info.jiuyou.codediy_sdk.utils.UUIDGenerator
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/18 0018  12:49
 * des ：
 */
data class Hello(val id: Int, val login: String, val name: String, val avatar_url: String)

interface HelloAPI {
    fun hello(limit: Int): String
}

class HelloEvent(override val uuid: String) : BaseEvent<Hello>(uuid)

interface HelloService {
    @GET("hello.json")
    fun hello(@Query("limit") limit: Int): Call<Hello>
}

class HelloImpl(context: Context) : BaseImpl<HelloService>(context), HelloAPI {
    override fun hello(limit: Int): String {
        val uuid = UUIDGenerator.getUUID()
        mService.hello(limit).enqueue(BaseCallback(HelloEvent(uuid)))
        return uuid
    }
}