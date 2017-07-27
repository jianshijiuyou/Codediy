package info.jiuyou.codediy.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.gcssloop.diycode_sdk.api.topic.bean.Topic
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicsListEvent
import info.jiuyou.codediy.fragment.base.RefreshRecyclerFragment
import info.jiuyou.codediy.viewbinder.TopicViewBinder
import kotlinx.android.synthetic.main.fragment_main_base.*
import me.drakeet.multitype.MultiTypeAdapter

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/20 0020  14:11
 * des ：
 */
class MyCollectListFragment : RefreshRecyclerFragment<Topic, GetTopicsListEvent>() {

    private lateinit var loginName:String

    companion object {
        fun newInstance(loginName:String):MyCollectListFragment{
            val fragment=MyCollectListFragment()
            val b=Bundle()
            b.putString("loginName",loginName)
            fragment.arguments=b
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginName=arguments.getString("loginName")
    }

    override fun setAdapterRegister(adapter: MultiTypeAdapter) {
        adapter.register(Topic::class.java, TopicViewBinder(activity))
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(context)

    override fun initData(adapter: MultiTypeAdapter) {
        refreshLayout.autoRefresh()
    }

    override fun request(offset: Int, limit: Int) = diycode.getUserCollectionTopicList(loginName, offset, limit)!!

}