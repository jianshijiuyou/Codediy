package info.jiuyou.codediy_sdk.api.base.callback

import info.jiuyou.codediy_sdk.api.base.bean.Token
import info.jiuyou.codediy_sdk.api.base.BaseEvent
import info.jiuyou.codediy_sdk.utils.TokenUtil
import retrofit2.Call
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
 * 创建日期 ：2017/7/18 0018  9:38
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
class TokenCallback(val tokenCache: TokenUtil, event: BaseEvent<Token>) : BaseCallback<Token>(event) {
    override fun onResponse(call: Call<Token>, response: Response<Token>) {
        super.onResponse(call, response)
        if (response.isSuccessful) {
            tokenCache.saveToken(response.body())
        }
    }
}