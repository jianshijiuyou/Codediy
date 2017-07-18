package info.jiuyou.codediy_sdk.api.user

import java.io.Serializable

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/18 0018  17:10
 * des ：
 */

data class User(val id: Int, val login: String, val name: String, val avatar_url: String) : Serializable

data class UserDetail(
        val id: Int,
        val login: String,
        val name: String,
        val avatar_url: String,
        val location: String,
        val company: String,
        val twitter: String,
        val website: String,
        val bio: String,
        val tagline: String,
        val github: String,
        val created_at: String,
        val email: String,
        val topics_count: Int = 0,
        val replies_count: Int = 0,
        val following_count: Int = 0,
        val followers_count: Int = 0,
        val favorites_count: Int = 0,
        val level: String,
        val level_name: String
) : Serializable