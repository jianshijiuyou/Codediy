package info.jiuyou.codediy_sdk.api.login.api

import info.jiuyou.codediy_sdk.api.base.bean.State
import info.jiuyou.codediy_sdk.api.base.bean.Token
import info.jiuyou.codediy_sdk.api.base.config.URLConstant
import retrofit2.Call
import retrofit2.http.*

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/18 0018  13:50
 * des ：
 */
interface LoginService {
    /**
     * 获取 Token (一般在登录时调用)
     *
     * @param client_id     客户端 id
     * @param client_secret 客户端私钥
     * @param grant_type    授权方式 - 密码
     * @param username      用户名
     * @param password      密码
     * @return Token 实体类
     */
    @POST(URLConstant.OAUTH_URL)
    @FormUrlEncoded
    fun getToken(@Field("client_id") client_id: String, @Field("client_secret") client_secret: String,
                 @Field("grant_type") grant_type: String, @Field("username") username: String,
                 @Field("password") password: String): Call<Token>

    /**
     * 刷新 token
     *
     * @param client_id     客户端 id
     * @param client_secret 客户端私钥
     * @param grant_type    授权方式 - Refresh Token
     * @param refresh_token token 信息
     * @return Token 实体类
     */
    @POST(URLConstant.OAUTH_URL)
    @FormUrlEncoded
    fun refreshToken(@Field("client_id") client_id: String, @Field("client_secret") client_secret: String,
                     @Field("grant_type") grant_type: String, @Field("refresh_token") refresh_token: String): Call<Token>

    /**
     * 记录用户 Device 信息，用于 Push 通知。
     * 请在每次用户打开 App 的时候调用此 API 以便更新 Token 的 last_actived_at 让服务端知道这个设备还活着。
     * Push 将会忽略那些超过两周的未更新的设备。
     *
     * @param platform 平台 ["ios", "android"]
     * @param token    令牌 token
     * @return 是否成功
     */
    @POST("devices.json")
    @FormUrlEncoded
    fun updateDevices(@Field("platform") platform: String, @Field("token") token: String): Call<State>

    /**
     * 删除 Device 信息，请注意在用户登出或删除应用的时候调用，以便能确保清理掉

     * @param platform 平台 ["ios", "android"]
     * *
     * @param token    令牌 token
     * *
     * @return 是否成功
     */
    @DELETE("devices.json")
    fun deleteDevices(@Query("platform") platform: String, @Query("token") token: String): Call<State>
}