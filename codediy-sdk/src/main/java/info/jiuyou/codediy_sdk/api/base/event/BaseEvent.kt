package info.jiuyou.codediy_sdk.api.base.event

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
    protected var ok: Boolean = false

    init {
        ok = data != null
    }

    fun setEvent(code: Int, data: T): BaseEvent<T> {
        ok = true
        this.code = code
        this.data = data
        return this
    }
}

fun main(args: Array<String>) {
    val b = BaseEvent<String>("")
}