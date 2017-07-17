package info.jiuyou.codediy_sdk.api.base.event

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/7/17 0017  16:32
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
open class BaseEvent<T>(val uuid: String, var code: Int = -1, var data: T? = null) {

    val codeDes by CodeDesDelegate(code)
    fun isOk() = data != null
    fun exist(block: BaseEvent<T>.() -> Unit) {
        if (isOk()) {
            block(this)
        }
    }

}

class CodeDesDelegate(val code: Int) : ReadOnlyProperty<Any?, String> {

    val codeString = mutableMapOf<Int, String?>()

    init {
        codeString[-1] = "可能是网络未连接"
        codeString[200] = "请求成功，或执行成功。"
        codeString[201] = codeString[200]
        codeString[400] = "参数不符合 API 的要求、或者数据格式验证没有通过"
        codeString[401] = "用户认证失败，或缺少认证信息，比如 access_token 过期，或没传，可以尝试用 refresh_token 方式获得新的 access_token"
        codeString[402] = "用户尚未登录"
        codeString[403] = "当前用户对资源没有操作权限"
        codeString[404] = "资源不存在"
        codeString[500] = "服务器异常"
    }


    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return if (codeString[code] != null) codeString[code]!! else "未知异常($code)"
    }

}



