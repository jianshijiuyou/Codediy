package info.jiuyou.codediy.base.app

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.gcssloop.diycode_sdk.api.Diycode
import info.jiuyou.codediy.R
import info.jiuyou.codediy.hcakpatch.IMMLeaks

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/19 0019  8:51
 * des ：
 */
abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var mDiycode: Diycode
    protected lateinit var viewHolder: ViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDiycode = Diycode.getSingleInstance()
        viewHolder = ViewHolder(layoutInflater, null, getLayoutId())
        setContentView(viewHolder.rootView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            IMMLeaks.fixFocusedViewLeak(this.application)// 修复 InputMethodManager 引发的内存泄漏
        }

        initActionBar()
        initData()
        initViews()
    }


    abstract fun getLayoutId(): Int


    fun initActionBar() {
        val toolbar = viewHolder[R.id.toolbar]
        if (toolbar != null) {
            setSupportActionBar(toolbar as Toolbar)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }


    abstract fun initData()
    abstract fun initViews()
}