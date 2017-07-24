package info.jiuyou.codediy.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.gcssloop.diycode_sdk.api.topic.bean.Topic
import com.gcssloop.diycode_sdk.api.topic.bean.TopicContent
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicEvent
import info.jiuyou.codediy.R
import info.jiuyou.codediy.base.app.BaseActivity
import info.jiuyou.codediy.utils.DataCache
import info.jiuyou.codediy.viewbinder.MarkdownViewBinder
import info.jiuyou.codediy.viewbinder.TopicViewBinder
import info.jiuyou.codediy.widget.MarkdownView
import kotlinx.android.synthetic.main.activity_topic_content.*
import me.drakeet.multitype.MultiTypeAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.share

class TopicContentActivity : BaseActivity(), AnkoLogger {

    private lateinit var topic: Topic
    private var topicContent: TopicContent? = null
    private lateinit var dataCache: DataCache
    private lateinit var adapter: MultiTypeAdapter
    private val requestType = mutableMapOf<String, String>()
    private lateinit var markdownViewBinder:MarkdownViewBinder

    companion object {
        val REQUEST_TYPE_TOPIC_CONTENT = "REQUEST_TYPE_TOPIC_CONTENT"
        val REQUEST_TYPE_REPLY = "REQUEST_TYPE_REPLY"
    }


    override fun getLayoutId() = R.layout.activity_topic_content

    override fun initData() {
        topic = intent.getSerializableExtra("topic") as Topic
    }


    override fun initViews() {
        title = "话题"
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MultiTypeAdapter()
        adapter.register(Topic::class.java, TopicViewBinder(this, false))
        markdownViewBinder=MarkdownViewBinder(this)
        adapter.register(String::class.java, markdownViewBinder)
        adapter.addData(topic)
        recyclerView.adapter = adapter
        dataCache = DataCache(this)
        isRequestData()
    }

    fun isRequestData() {
        topicContent = dataCache.getTopicContent(topic.id)
        if (topicContent == null || topicContent?.updated_at != topic.updated_at) {
            //请求数据
            val uuid = mDiycode.getTopic(topic.id)
            requestType.put(uuid, REQUEST_TYPE_TOPIC_CONTENT)
        } else {
            info("使用的缓存数据")
            adapter.addData(topicContent?.body)
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onResponse(event: GetTopicEvent) {
        when (requestType[event.uuid]) {
            REQUEST_TYPE_TOPIC_CONTENT -> {
                topicContent = event.bean
                adapter.addData(topicContent?.body)
                dataCache.saveTopicContent(topicContent)
            }
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

    override fun onDestroy() {
        super.onDestroy()
        markdownViewBinder.clearWebViewResource()
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
