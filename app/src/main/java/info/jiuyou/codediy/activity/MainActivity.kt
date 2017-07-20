package info.jiuyou.codediy.activity

import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import info.jiuyou.codediy.R
import info.jiuyou.codediy.base.app.BaseActivity
import info.jiuyou.codediy.fragment.base.RefreshRecyclerFragment
import info.jiuyou.codediy.fragment.base.SimpleRefreshRecyclerFragment
import info.jiuyou.codediy.utils.Config
import info.jiuyou.codediy.utils.DataCache
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast


class MainActivity : BaseActivity(), AnkoLogger, NavigationView.OnNavigationItemSelectedListener {

    private var isToolbarFirstClick = true
    private lateinit var mCache: DataCache
    private val mConfig = Config.instance()
    private var mCurrentPosition = 0
    override fun getLayoutId() = R.layout.activity_main


    override fun initViews() {
        EventBus.getDefault().register(this)

        mCache = DataCache(this)

        initMenu()
        initViewPager()
    }

    fun initViewPager() {
        viewPager.offscreenPageLimit = 3

        val fragment1 = SimpleRefreshRecyclerFragment()
        val fragment2 = SimpleRefreshRecyclerFragment()
        val fragment3 = SimpleRefreshRecyclerFragment()
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            val titles = arrayOf("Topics", "News", "Sites")
            override fun getItem(position: Int) = when (position) {
                0 -> fragment1
                1 -> fragment2
                2 -> fragment3
                else -> {
                    throw NullPointerException("没有找到对应的fragment")
                }
            }

            override fun getCount() = 3

            override fun getPageTitle(position: Int): CharSequence {
                return titles[position]
            }
        }

        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                mCurrentPosition = position
            }
        })

        mCurrentPosition = mConfig?.getMainViewPagerPosition() ?: 0
        viewPager.currentItem = mCurrentPosition
        tabLayout.setupWithViewPager(viewPager)

    }


    fun initMenu() {
        //toolbar
        toolbar.setLogo(R.drawable.logo_actionbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val detector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent?): Boolean {
                quickToTop()
                return super.onDoubleTap(e)
            }
        })
        toolbar.setOnTouchListener { _, event ->
            detector.onTouchEvent(event)
            false
        }
        toolbar.setOnClickListener {
            if (isToolbarFirstClick) {
                isToolbarFirstClick = false
                toast("双击标题栏快速返回顶部")
            }
        }

        //drawer
        val drawer = drawer_layout
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        //navigation
        navigation.itemIconTintList = null
        navigation.setNavigationItemSelectedListener(this)


        //FloatingActionButton
        fab.onClick {
            quickToTop()
        }

        loadMenuData()
    }

    fun loadMenuData() {

    }


    fun quickToTop() {
        toast("返回顶部")
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTest(s: String) {

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_menu_post -> {
                toast("nav_menu_post")
            }
            R.id.nav_menu_collect -> {
                toast("nav_menu_collect")
            }
            R.id.nav_menu_setting -> {
                toast("nav_menu_setting")
            }
            R.id.nav_menu_about -> {
                toast("nav_menu_about")
            }
        }

        return true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_notification -> {
                toast("通知")
                return true
            }
            R.id.action_settings -> {
                toast("设置")
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        mConfig?.saveMainViewPagerPosition(mCurrentPosition)
        super.onDestroy()
    }
}
