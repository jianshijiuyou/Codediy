package info.jiuyou.codediy.fragment

import android.support.v7.widget.LinearLayoutManager
import com.gcssloop.diycode_sdk.api.news.bean.New
import com.gcssloop.diycode_sdk.api.news.event.GetNewsListEvent
import info.jiuyou.codediy.fragment.base.RefreshRecyclerFragment
import info.jiuyou.codediy.viewbinder.NewsViewBinder
import kotlinx.android.synthetic.main.fragment_main_base.*
import me.drakeet.multitype.MultiTypeAdapter

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/20 0020  14:11
 * des ：
 */
class NewsListFragment : RefreshRecyclerFragment<New, GetNewsListEvent>() {
    override fun setAdapterRegister(adapter: MultiTypeAdapter) {
        adapter.register(New::class.java, NewsViewBinder(activity))
    }

    override fun getLayoutManager() = LinearLayoutManager(context)

    override fun initData(adapter: MultiTypeAdapter) {
        val list = mDataCache.newsListObj
        if (list == null || list.size <= 0) {
            refreshLayout.autoRefresh()
            return
        }

        pageIndex = mConfig?.getNewsListPageIndex() ?: 0

        adapter.setDatas(list)

        val lastPosition = mConfig?.getNewsListLastPosition() ?: 0
        recyclerView.layoutManager.scrollToPosition(lastPosition)
    }

    override fun request(offset: Int, limit: Int) = diycode.getNewsList(null, offset, limit)

    override fun onRefresh(adapter: MultiTypeAdapter, event: GetNewsListEvent) {
        super.onRefresh(adapter, event)
        mDataCache.saveNewsListObj(adapter.items)
    }

    override fun onLoadMore(adapter: MultiTypeAdapter, event: GetNewsListEvent) {
        super.onLoadMore(adapter, event)
        mDataCache.saveNewsListObj(adapter.items)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mConfig?.saveNewsListPageIndex(pageIndex)

        val view = recyclerView.layoutManager.getChildAt(0)
        val position = recyclerView.layoutManager.getPosition(view)
        mConfig?.saveNewsListPosition(position)
    }
}