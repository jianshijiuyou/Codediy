package info.jiuyou.codediy.fragment

import android.support.v7.widget.GridLayoutManager
import com.gcssloop.diycode_sdk.api.sites.bean.Sites
import com.gcssloop.diycode_sdk.api.sites.event.GetSitesEvent
import info.jiuyou.codediy.fragment.base.RefreshRecyclerFragment
import info.jiuyou.codediy.fragment.viewbinder.SiteViewBinder
import info.jiuyou.codediy.fragment.viewbinder.SitesViewBinder
import kotlinx.android.synthetic.main.fragment_main_base.*
import me.drakeet.multitype.MultiTypeAdapter
import java.io.Serializable

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/20 0020  14:11
 * des ：
 */
class SitesListFragment : RefreshRecyclerFragment<Sites, GetSitesEvent>() {
    override fun setAdapterRegister(adapter: MultiTypeAdapter) {
        adapter.register(Sites::class.java, SitesViewBinder(activity))
        adapter.register(Sites.Site::class.java, SiteViewBinder(activity))
    }

    override fun getLayoutManager(): GridLayoutManager {
        val manager = GridLayoutManager(activity, 2)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }
        return manager
    }

    override fun initData(adapter: MultiTypeAdapter) {

        refreshLayout.isLoadmoreFinished = true

        val list = mDataCache.getSitesItems<Serializable>()
        if (list == null || list.size <= 0) {
            refreshLayout.autoRefresh()
            return
        }
        adapter.setDatas(list.map { it as Any })
    }

    override fun request(offset: Int, limit: Int): String = diycode.sites

    override fun onRefresh(adapter: MultiTypeAdapter, event: GetSitesEvent) {
        onLoadMore(adapter, event)
    }

    override fun onLoadMore(adapter: MultiTypeAdapter, event: GetSitesEvent) {
        val list = event.bean.flatMap { it.sites }
        adapter.setDatas(list)
        mDataCache.saveSitesItems(list)
    }
}