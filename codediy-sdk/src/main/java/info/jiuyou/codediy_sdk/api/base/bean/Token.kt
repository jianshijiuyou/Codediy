package info.jiuyou.codediy_sdk.api.base.bean

import java.io.Serializable

/**
 * ==========================================
 *
 * 版   权 ：jianshijiuyou(c) 2017
 *
 * 作   者 ：wq
 *
 * 版   本 ：1.0
 *
 * 创建日期 ：2017/7/18 0018  9:39
 *
 * 描   述 ：
 *   {
 *   "access_token": "83fd8e3bc71fc062c14a934f62d7c9480a1201c45aa6986c5510c7ebb9334931",// 用户令牌(获取相关数据使用)
 *   "token_type": "bearer",// 令牌类型
 *   "expires_in": 5184000,// 过期时间
 *   "refresh_token": "72af780f5ea030ab875cfc34128a7a567357b0a678b7beef05e178dcd17a8c26",// 刷新令牌(获取新的令牌)
 *   "created_at": 1500342278// 创建时间
 *   }
 *
 * 修订历史 ：
 *
 * ==========================================
 */
data class Token(val access_token: String, val token_type: String, val expires_in: Int, val refresh_token: String, val created_at: Long):Serializable