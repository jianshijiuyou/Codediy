package info.jiuyou.codediy.activity

import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.gcssloop.diycode_sdk.api.topic.bean.Topic
import com.gcssloop.diycode_sdk.api.user.bean.User
import com.gcssloop.diycode_sdk.api.user.event.GetUserCreateTopicListEvent
import com.gcssloop.diycode_sdk.api.user.event.GetUserEvent
import info.jiuyou.codediy.R
import info.jiuyou.codediy.base.app.BaseActivity
import info.jiuyou.codediy.utils.ImageUtils
import info.jiuyou.codediy.viewbinder.TopicViewBinder
import kotlinx.android.synthetic.main.activity_user.*
import me.drakeet.multitype.MultiTypeAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class UserActivity : BaseActivity() {

    private lateinit var user: User

    override fun getLayoutId() = R.layout.activity_user

    override fun initData() {
        user = intent.getSerializableExtra("user") as User
        mainCollapsing.title=user.login
    }

    override fun initViews() {
        mDiycode.getUser(user.login)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUserResponse(event: GetUserEvent) {
        if (event.isOk) {
            event.bean.apply {
                tv_email.text="email:$email"
                tv_fff.text="following:$following_count   followers:$followers_count   favorites:$favorites_count"
                tv_tagline.text=if(!TextUtils.isEmpty(tagline))tagline else "这个人很懒，什么都不想说..."
                ImageUtils.loadImage(this@UserActivity,avatar_url,imgAvatar)
                mDiycode.getUserCreateTopicList(user.login, null, 0, topics_count)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTopicsResponse(event: GetUserCreateTopicListEvent) {
        if (event.isOk) {
            recyclerView.layoutManager=LinearLayoutManager(this)
            val adapter=MultiTypeAdapter(event.bean as List<Any>)
            adapter.register(Topic::class.java,TopicViewBinder(this,true,false,false))
            recyclerView.adapter=adapter
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}
