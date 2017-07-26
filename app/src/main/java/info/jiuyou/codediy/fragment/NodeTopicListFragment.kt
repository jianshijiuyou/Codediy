package info.jiuyou.codediy.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.gcssloop.diycode_sdk.api.topic.bean.Topic
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicsListEvent
import info.jiuyou.codediy.fragment.base.RefreshRecyclerFragment
import info.jiuyou.codediy.viewbinder.TopicViewBinder
import kotlinx.android.synthetic.main.fragment_main_base.*
import me.drakeet.multitype.MultiTypeAdapter

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/26 0026  10:13
 * des ：
 */
class NodeTopicListFragment : RefreshRecyclerFragment<Topic, GetTopicsListEvent>() {
    private var nodeId = 0

    companion object {
        fun newInstance(nodeId: Int): NodeTopicListFragment {
            val fragment = NodeTopicListFragment()
            val b = Bundle()
            b.putInt("nodeId", nodeId)
            fragment.arguments = b
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nodeId = arguments.getInt("nodeId")
    }


    override fun setAdapterRegister(adapter: MultiTypeAdapter) {
        adapter.register(Topic::class.java, TopicViewBinder(activity,true,false))
    }

    override fun getLayoutManager() = LinearLayoutManager(activity)

    override fun initData(adapter: MultiTypeAdapter) {
        refreshLayout.autoRefresh()
    }

    override fun request(offset: Int, limit: Int) = diycode.getTopicsList(null, nodeId, offset, limit)

}