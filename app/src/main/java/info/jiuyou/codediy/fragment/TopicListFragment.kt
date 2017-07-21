package info.jiuyou.codediy.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.gcssloop.diycode_sdk.api.topic.bean.Topic
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicsListEvent
import info.jiuyou.codediy.fragment.base.RefreshRecyclerFragment
import info.jiuyou.codediy.fragment.viewbinder.TopicViewBinder
import kotlinx.android.synthetic.main.fragment_main_base.*
import me.drakeet.multitype.MultiTypeAdapter

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/20 0020  14:11
 * des ：
 */
class TopicListFragment : RefreshRecyclerFragment<Topic, GetTopicsListEvent>() {
    override fun setAdapterRegister(adapter: MultiTypeAdapter) {
        adapter.register(Topic::class.java, TopicViewBinder(activity))
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(context)

    override fun initData(adapter: MultiTypeAdapter) {
        val list = mDataCache.topicsListObj
        if (list == null || list.size <= 0) {
            refreshLayout.autoRefresh()
            return
        }
        pageIndex = mConfig?.getTopicListLastPosition() ?: 0
        adapter.setDatas(list)

        val lastPosition = mConfig?.getTopicListLastPosition() ?: 0
        recyclerView.scrollToPosition(lastPosition)
    }

    override fun request(offset: Int, limit: Int) = diycode.getTopicsList(null, null, offset, limit)!!

    override fun onRefresh(adapter: MultiTypeAdapter, event: GetTopicsListEvent) {
        super.onRefresh(adapter, event)
        mDataCache.saveTopicsListObj(adapter.items)
    }

    override fun onLoadMore(adapter: MultiTypeAdapter, event: GetTopicsListEvent) {
        super.onLoadMore(adapter, event)
        mDataCache.saveTopicsListObj(adapter.items)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mConfig?.saveTopicListPageIndex(pageIndex)
        val view = recyclerView.layoutManager.getChildAt(0)
        val position = recyclerView.layoutManager.getPosition(view)
        mConfig?.saveTopicListState(position, 0)
    }
}