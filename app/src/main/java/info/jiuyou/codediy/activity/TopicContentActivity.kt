package info.jiuyou.codediy.activity

import android.app.ProgressDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.gcssloop.diycode_sdk.api.login.event.LoginEvent
import com.gcssloop.diycode_sdk.api.topic.bean.Topic
import com.gcssloop.diycode_sdk.api.topic.bean.TopicContent
import com.gcssloop.diycode_sdk.api.topic.bean.TopicReply
import com.gcssloop.diycode_sdk.api.topic.event.CreateTopicReplyEvent
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicEvent
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicRepliesListEvent
import info.jiuyou.codediy.R
import info.jiuyou.codediy.base.app.BaseActivity
import info.jiuyou.codediy.utils.DataCache
import info.jiuyou.codediy.viewbinder.*
import kotlinx.android.synthetic.main.activity_topic_content.*
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.*

class TopicContentActivity : BaseActivity(), AnkoLogger {

    private lateinit var topic: Topic
    private var topicContent: TopicContent? = null
    private lateinit var dataCache: DataCache
    private lateinit var adapter: MultiTypeAdapter
    private val requestType = mutableMapOf<String, String>()
    private lateinit var markdownViewBinder: MarkdownViewBinder
    private lateinit var cvb: CommentViewBinder
    private var dialog: ProgressDialog? = null
//    companion object {
//        val REQUEST_TYPE_TOPIC_CONTENT = "REQUEST_TYPE_TOPIC_CONTENT"
//        val REQUEST_TYPE_REPLY = "REQUEST_TYPE_REPLY"
//    }


    override fun getLayoutId() = R.layout.activity_topic_content

    override fun initData() {
        topic = intent.getSerializableExtra("topic") as Topic
        EventBus.getDefault().register(this)
    }


    override fun initViews() {
        title = "话题"
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MultiTypeAdapter()
        adapter.register(Topic::class.java, TopicViewBinder(this, false))
        markdownViewBinder = MarkdownViewBinder(this)
        adapter.register(String::class.java, markdownViewBinder)
        adapter.register(TopicReply::class.java, TopicReplyViewBinder(this))
        cvb=CommentViewBinder(this) {
            dialog = indeterminateProgressDialog(message = "正在加载…")
            mDiycode.createTopicReply(topic.id, it)
        }
        adapter.register(CommentViewBinder.Comment::class.java, cvb)
        adapter.register(LoginViewBinder.Login::class.java, LoginViewBinder(this))
        adapter.addData(topic)
        recyclerView.adapter = adapter
        dataCache = DataCache(this)
        isRequestData()
    }

    fun isRequestData() {
        topicContent = dataCache.getTopicContent(topic.id)
        if (topicContent == null || topicContent?.updated_at != topic.updated_at) {
            //请求数据
            mDiycode.getTopic(topic.id)

        } else {
            info("使用的缓存内容")
            adapter.addData(topicContent?.body)
            val replyList = dataCache.getTopicRepliesList(topic.id)
            if (replyList == null || replyList.size != topicContent?.replies_count) {
                mDiycode.getTopicRepliesList(topic.id, 0, topicContent?.replies_count)
            } else {
                info("使用的缓存回复")
                adapter.addDatas(replyList as List<Any>)
                //评论
                comment()
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTopicContentResponse(event: GetTopicEvent) {

        if (event.isOk) {
            topicContent = event.bean
            adapter.addData(topicContent?.body)
            dataCache.saveTopicContent(topicContent)
            mDiycode.getTopicRepliesList(topic.id, 0, topicContent?.replies_count)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRelpyListResPonse(event: GetTopicRepliesListEvent) {
        dialog?.dismiss()
        if (event.isOk) {
            val replyList = event.bean
            if (adapter.items.size == 2) {
                adapter.addDatas(replyList as List<Any>)

            } else {
                val items = Items()
                items.addAll(adapter.items.take(2))
                items.addAll(replyList)
                adapter.setDatas(items)
            }
            dataCache.saveTopicRepliesList(topic.id, replyList)

        }
        //评论
        comment()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginResponse(event: LoginEvent) {
        if (event.isOk) {
            toast("已经登录了")
            adapter.items.removeAt(adapter.items.size - 1)
            adapter.items.add(CommentViewBinder.Comment())
            adapter.notifyItemChanged(adapter.items.size - 1)
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCreateReply(event: CreateTopicReplyEvent) {
        if (event.isOk) {
            cvb.clear()
            topic.replies_count+=1
            topicContent!!.replies_count += 1
            mDiycode.getTopicRepliesList(topic.id, 0, topicContent!!.replies_count)
        } else {
            toast("回复失败")
        }
    }


    fun comment() {
        if (mDiycode.isLogin) {
            adapter.addData(CommentViewBinder.Comment())
        } else {
            adapter.addData(LoginViewBinder.Login())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        markdownViewBinder.clearWebViewResource()
        EventBus.getDefault().unregister(this)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.topic_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.action_share -> {
                val url = "https://www.diycode.cc/topics/${topic.id}"
                share(topic.title, url)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
