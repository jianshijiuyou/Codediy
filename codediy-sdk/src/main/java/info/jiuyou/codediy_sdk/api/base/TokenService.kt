package info.jiuyou.codediy_sdk.api.base

import info.jiuyou.codediy_sdk.api.base.bean.Token
import info.jiuyou.codediy_sdk.api.base.config.URLConstant
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/18 0018  11:40
 * des ：
 */
interface TokenService {
    /**
     * 刷新 token
     */
    @POST(URLConstant.OAUTH_URL)
    @FormUrlEncoded
    fun refreshToken(@Field("client_id") client_id: String, @Field("client_secret") client_secret: String,
                     @Field("grant_type") grant_type: String, @Field("refresh_token") refresh_token: String): Call<Token>
}