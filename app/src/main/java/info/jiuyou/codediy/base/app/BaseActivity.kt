package info.jiuyou.codediy.base.app

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.gcssloop.diycode_sdk.api.Diycode
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.PermissionListener
import info.jiuyou.codediy.R
import info.jiuyou.codediy.hcakpatch.IMMLeaks
import org.jetbrains.anko.find

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/19 0019  8:51
 * des ：
 */
abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var mDiycode: Diycode
    //protected lateinit var viewHolder: ViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDiycode = Diycode.getSingleInstance()
        //viewHolder = ViewHolder(layoutInflater, null, getLayoutId())
        setContentView(getLayoutId())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            IMMLeaks.fixFocusedViewLeak(this.application)// 修复 InputMethodManager 引发的内存泄漏
        }
        // 申请权限。
        AndPermission.with(this)
                .requestCode(100)
                .permission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .rationale { _, rationale -> AndPermission.rationaleDialog(this, rationale).show() }
                .callback(listener)
                .start()
        initActionBar()
        initData()
        initViews()
    }


    abstract fun getLayoutId(): Int


    fun initActionBar() {
        try {
            val toolbar = find<Toolbar>(R.id.toolbar)
            if (toolbar != null) {
                setSupportActionBar(toolbar)
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } catch (e: Exception) {
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }


    protected open fun initData() {}
    abstract fun initViews()

    private val listener = object : PermissionListener {
        override fun onSucceed(requestCode: Int, grantedPermissions: List<String>) {

        }

        override fun onFailed(requestCode: Int, deniedPermissions: List<String>) {

            if (AndPermission.hasAlwaysDeniedPermission(this@BaseActivity, deniedPermissions)) {

                AndPermission.defaultSettingDialog(this@BaseActivity, 123).show()

            }
        }
    }
}