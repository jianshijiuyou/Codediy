package info.jiuyou.codediy_sdk.utils

import java.util.*

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/18 0018  13:03
 * des ：
 */
object UUIDGenerator {
    fun getUUID(): String = UUID.randomUUID().toString()
    fun getUUID(number: Int) = Array(number) { getUUID() }
}