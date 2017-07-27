package info.jiuyou.codediy.activity

import info.jiuyou.codediy.R
import info.jiuyou.codediy.base.app.BaseActivity
import info.jiuyou.codediy.fragment.MyCollectListFragment

class MyCollectActivity : BaseActivity() {


    private lateinit var loginName: String

    override fun getLayoutId() = R.layout.activity_my_collect

    override fun initData() {
        super.initData()
        loginName = intent.getStringExtra("loginName")

        title="我的收藏"
    }

    override fun initViews() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_content, MyCollectListFragment.newInstance(loginName)).commit()
    }

}
