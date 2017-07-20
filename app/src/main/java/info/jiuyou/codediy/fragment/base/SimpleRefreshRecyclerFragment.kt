package info.jiuyou.codediy.fragment.base

import android.support.v7.widget.RecyclerView
import com.gcssloop.diycode_sdk.api.topic.bean.Topic
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicEvent
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicsListEvent
import me.drakeet.multitype.MultiTypeAdapter

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：17-7-20  下午11:17
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
class SimpleRefreshRecyclerFragment :RefreshRecyclerFragment<Topic,GetTopicsListEvent>(){
    override fun onRefresh(adapter: MultiTypeAdapter, event: GetTopicsListEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadMore(adapter: MultiTypeAdapter, event: GetTopicsListEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun noMore() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setAdapterRegister(adapter: MultiTypeAdapter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initData(adapter: MultiTypeAdapter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun request(offset: Int, limit: Int): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(event: GetTopicsListEvent, type: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}