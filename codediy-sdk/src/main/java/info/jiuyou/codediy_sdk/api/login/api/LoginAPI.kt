package info.jiuyou.codediy_sdk.api.login.api

import info.jiuyou.codediy_sdk.api.base.bean.Token

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/18 0018  13:49
 * des ：
 */
interface LoginAPI {
    /**
     * 登录时调用
     * 返回一个 token，用于获取各类私有信息使用，该 token 用 LoginEvent 接收。
     * @see LoginEvent
     */
    fun login(name: String, password: String): String

    /**
     * 刷新 token
     *
     * @see RefreshTokenEvent
     */
    fun refreshToken(): String

    fun logout(): String
    fun isLogin(): Boolean
    fun getCacheToken(): Token?
    fun updateDevices(): String
    fun deleteDevices(): String
}