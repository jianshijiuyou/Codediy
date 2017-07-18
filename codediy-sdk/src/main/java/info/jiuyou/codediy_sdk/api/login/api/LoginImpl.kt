package info.jiuyou.codediy_sdk.api.login.api

import android.content.Context
import info.jiuyou.codediy_sdk.api.base.BaseImpl
import info.jiuyou.codediy_sdk.api.base.bean.Token
import info.jiuyou.codediy_sdk.api.base.callback.BaseCallback
import info.jiuyou.codediy_sdk.api.base.callback.TokenCallback
import info.jiuyou.codediy_sdk.api.base.config.OAuth
import info.jiuyou.codediy_sdk.api.login.*
import org.greenrobot.eventbus.EventBus

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/18 0018  13:45
 * des ：
 */


class LoginImpl(context: Context) : BaseImpl<LoginService>(context), LoginAPI {
    override fun logout() = baseFun {
        tokenUtil.clearToken()    // 清除token
        EventBus.getDefault().post(LogoutEvent(it, 0, "用户登出"))
    }

    override fun isLogin(): Boolean = tokenUtil.getToken() != null

    override fun getCacheToken(): Token? = tokenUtil.getToken()

    override fun updateDevices() = baseFun {
        mService.updateDevices("android", tokenUtil.getToken()!!.access_token)
                .enqueue(BaseCallback(UpdateDevicesEvent(it)))
    }

    override fun deleteDevices() = baseFun {
        mService.deleteDevices("android", tokenUtil.getToken()!!.access_token)
                .enqueue(BaseCallback(DeleteDevicesEvent(it)))
    }

    override fun login(name: String, password: String) = baseFun {
        mService.getToken(OAuth.client_id, OAuth.client_secret, OAuth.GRANT_TYPE_LOGIN, name, password)
                .enqueue(TokenCallback(tokenUtil, LoginEvent(it)))
    }

    override fun refreshToken() = baseFun {
        if (tokenUtil.getToken() == null) {
            EventBus.getDefault().post(RefreshTokenEvent(it, 401, null))
        } else {
            mService.refreshToken(OAuth.client_id, OAuth.client_secret, OAuth.GRANT_TYPE_REFRESH, tokenUtil.getToken()!!.refresh_token)
                    .enqueue(TokenCallback(tokenUtil, RefreshTokenEvent(it)))
        }

    }
}