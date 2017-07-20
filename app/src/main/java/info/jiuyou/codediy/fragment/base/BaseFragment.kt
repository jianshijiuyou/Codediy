package info.jiuyou.codediy.fragment.base

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gcssloop.diycode_sdk.api.Diycode
import info.jiuyou.codediy.base.app.ViewHolder
import info.jiuyou.codediy.utils.Config
import info.jiuyou.codediy.utils.DataCache

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/20 0020  14:27
 * des ：
 */
open abstract class BaseFragment : Fragment() {

    protected lateinit var viewHolder: ViewHolder

    protected val mConfig = Config.instance()
    protected val diycode = Diycode.getSingleInstance()
    protected lateinit var mDataCache :DataCache

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataCache=DataCache(context)
    }

    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        viewHolder = ViewHolder(inflater, container, getLayoutId())
        return viewHolder.rootView
    }

    abstract fun getLayoutId(): Int


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    abstract fun initViews()
}