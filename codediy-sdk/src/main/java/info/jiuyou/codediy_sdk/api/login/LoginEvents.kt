package info.jiuyou.codediy_sdk.api.login

import info.jiuyou.codediy_sdk.api.base.BaseEvent
import info.jiuyou.codediy_sdk.api.base.bean.State
import info.jiuyou.codediy_sdk.api.base.bean.Token

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/18 0018  13:50
 * des ：
 */
class LoginEvent(override val uuid: String) : BaseEvent<Token>(uuid)
class RefreshTokenEvent(override val uuid: String, code: Int = -1, data: Token? = null) : BaseEvent<Token>(uuid, code, data)
class LogoutEvent(override val uuid: String, override var code: Int=-1, override var data: String?=null) : BaseEvent<String>(uuid, code, data)
class UpdateDevicesEvent(override val uuid:String) : BaseEvent<State>(uuid)
class DeleteDevicesEvent(override val uuid:String) : BaseEvent<State>(uuid)