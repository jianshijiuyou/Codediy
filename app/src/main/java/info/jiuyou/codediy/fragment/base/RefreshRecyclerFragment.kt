package info.jiuyou.codediy.fragment.base


import info.jiuyou.codediy.R
import kotlinx.android.synthetic.main.fragment_main_base.*
import me.drakeet.multitype.MultiTypeAdapter


/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/20 0020  14:43
 * des ：
 */
class RefreshRecyclerFragment : BaseFragment() {
    companion object {
        fun newInstance()=RefreshRecyclerFragment()
    }

    private lateinit var adapter:MultiTypeAdapter


    override fun getLayoutId(): Int = R.layout.fragment_main_base

    override fun initViews() {

        refreshLayout.setOnRefreshListener {
            refreshLayout.finishRefresh(2000)
        }

        refreshLayout.setOnLoadmoreListener {
            refreshLayout.finishLoadmore(false)
        }

        adapter= MultiTypeAdapter()
        adapter.register()
        recyclerView.adapter=adapter
    }
}