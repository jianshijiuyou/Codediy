package info.jiuyou.codediy.fragment.base


import android.support.v7.widget.RecyclerView
import com.gcssloop.diycode_sdk.api.base.event.BaseEvent
import info.jiuyou.codediy.R
import kotlinx.android.synthetic.main.fragment_main_base.*
import me.drakeet.multitype.MultiTypeAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.support.v4.toast


/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/20 0020  14:43
 * des ：
 */
abstract class RefreshRecyclerFragment<T, Event : BaseEvent<List<T>>> : BaseFragment() {

    companion object {
        val POST_TYPE_REFRESH = "post_type_refresh"
        val POST_TYPE_LOADMORE = "post_type_loadmore"
    }

    private val mPostTypes = mutableMapOf<String, String>()


    protected lateinit var adapter: MultiTypeAdapter
    protected var pageIndex = 0
    private var pageCount = 20


    override fun getLayoutId(): Int = R.layout.fragment_main_base

    override fun initViews() {

        refreshLayout.setOnRefreshListener { refresh() }

        refreshLayout.setOnLoadmoreListener { loadMore() }

        adapter = MultiTypeAdapter()
        setAdapterRegister(adapter)
        recyclerView.layoutManager = getLayoutManager()
        recyclerView.adapter = adapter

        initData(adapter)
    }

    private fun refresh() {
        val uuid = request(0, pageCount)
        mPostTypes[uuid] = POST_TYPE_REFRESH
    }

    private fun loadMore() {

        val uuid = request(pageIndex * pageCount, pageCount)
        mPostTypes[uuid] = POST_TYPE_LOADMORE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRespose(event: Event) {
        refreshLayoutFinish()
        val postType = mPostTypes[event.uuid] ?: ""
        if (event.isOk) {
            when (postType) {
                POST_TYPE_REFRESH -> {
                    pageIndex = 1
                    onRefresh(adapter, event)
                }
                POST_TYPE_LOADMORE -> {
                    pageIndex++
                    if (event.bean.size < pageCount) {
                        refreshLayout.isLoadmoreFinished = true
                    }
                    onLoadMore(adapter, event)
                }
            }
        } else {
            onError(event, postType)
        }
        mPostTypes.remove(event.uuid)
    }


    fun refreshLayoutFinish() {
        if (refreshLayout.isRefreshing) {
            refreshLayout.finishRefresh()
        }
        if (refreshLayout.isLoading) {
            refreshLayout.finishLoadmore()
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

    fun quickToTop() {
        recyclerView.smoothScrollToPosition(0)
    }

    protected open fun onRefresh(adapter: MultiTypeAdapter, event: Event) {
        adapter.setDatas(event.bean)
    }

    protected open fun onLoadMore(adapter: MultiTypeAdapter, event: Event) {
        adapter.addDatas(event.bean)
    }

    protected open fun onError(event: Event, type: String) {
        when (type) {
            POST_TYPE_REFRESH -> {
                toast("刷新数据失败")
            }
            POST_TYPE_LOADMORE -> {
                toast("加载数据失败")
            }
        }
    }
    //==================================


    /**
     * 类型注册
     */
    abstract fun setAdapterRegister(adapter: MultiTypeAdapter)

    /**
     * 布局管理器
     */
    abstract fun getLayoutManager(): RecyclerView.LayoutManager

    /**
     * 数据初始化
     */
    abstract fun initData(adapter: MultiTypeAdapter)

    abstract fun request(offset: Int, limit: Int): String


}