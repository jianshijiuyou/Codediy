package info.jiuyou.codediy_sdk.utils

import android.content.Context
import info.jiuyou.codediy_sdk.api.base.bean.Token

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/7/18 0018  10:00
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
class TokenUtil(context: Context) {
    private val cache = ACache.get(context)

    fun saveToken(token: Token) {
        cache.put("token", token)
    }

    fun getToken(): Token? {
        val token = cache.getAsObject("token")
        if (token != null) {
            return token as Token
        } else {
            return null
        }
    }

    fun clearToken() {
        cache.remove("token")
    }
}

