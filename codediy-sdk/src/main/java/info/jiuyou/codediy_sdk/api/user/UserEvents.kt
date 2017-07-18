package info.jiuyou.codediy_sdk.api.user

import info.jiuyou.codediy_sdk.api.base.BaseEvent
import info.jiuyou.codediy_sdk.api.base.bean.State

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/18 0018  17:12
 * des ：
 */
class BlockUserEvent(override val uuid: String, override var code: Int = -1, override var data: State? = null) : BaseEvent<State>(uuid, code, data)

class FollowUserEvent(override val uuid: String, override var code: Int = -1, override var data: State? = null) : BaseEvent<State>(uuid, code, data)
class GetMeEvent(override val uuid: String, override var code: Int = -1, override var data: State? = null) : BaseEvent<State>(uuid, code, data)
class GetUserBlockedListEvent(override val uuid: String, override var code: Int = -1, override var data: State? = null) : BaseEvent<State>(uuid, code, data)
class GetUserCollectionTopicListEvent(override val uuid: String, override var code: Int = -1, override var data: State? = null) : BaseEvent<State>(uuid, code, data)
class GetUserCreateTopicListEvent(override val uuid: String, override var code: Int = -1, override var data: State? = null) : BaseEvent<State>(uuid, code, data)
class GetUserEvent(override val uuid: String, override var code: Int = -1, override var data: State? = null) : BaseEvent<State>(uuid, code, data)
class GetUserFollowerListEvent(override val uuid: String, override var code: Int = -1, override var data: State? = null) : BaseEvent<State>(uuid, code, data)
class GetUserFollowingListEvent(override val uuid: String, override var code: Int = -1, override var data: State? = null) : BaseEvent<State>(uuid, code, data)
class GetUserReplyTopicListEvent(override val uuid: String, override var code: Int = -1, override var data: State? = null) : BaseEvent<State>(uuid, code, data)
class GetUsersListEvent(override val uuid: String, override var code: Int = -1, override var data: State? = null) : BaseEvent<State>(uuid, code, data)
class UnBlockUserEvent(override val uuid: String, override var code: Int = -1, override var data: State? = null) : BaseEvent<State>(uuid, code, data)
class UnFollowUserEvent(override val uuid: String, override var code: Int = -1, override var data: State? = null) : BaseEvent<State>(uuid, code, data)
