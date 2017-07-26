package info.jiuyou.codediy.activity

import android.graphics.Bitmap
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.gcssloop.diycode_sdk.api.login.event.LogoutEvent
import com.gcssloop.diycode_sdk.api.user.bean.User
import com.gcssloop.diycode_sdk.api.user.event.GetMeEvent
import info.jiuyou.codediy.R
import info.jiuyou.codediy.base.app.BaseActivity
import info.jiuyou.codediy.fragment.NewsListFragment
import info.jiuyou.codediy.fragment.SitesListFragment
import info.jiuyou.codediy.fragment.TopicListFragment
import info.jiuyou.codediy.fragment.base.BaseFragment
import info.jiuyou.codediy.utils.Config
import info.jiuyou.codediy.utils.DataCache
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class MainActivity : BaseActivity(), AnkoLogger, NavigationView.OnNavigationItemSelectedListener {

    private var isToolbarFirstClick = true
    private lateinit var mCache: DataCache
    private val mConfig = Config.instance()
    private var mCurrentPosition = 0
    private val fragmentList = mutableListOf<BaseFragment>()

    override fun getLayoutId() = R.layout.activity_main
//    override fun initData() {
//        mDiycode.logout()
//    }

    override fun initViews() {
        EventBus.getDefault().register(this)

        mCache = DataCache(this)

        initMenu()
        initViewPager()
    }

    fun initViewPager() {
        viewPager.offscreenPageLimit = 3

        val fragment1 = TopicListFragment()
        val fragment2 = NewsListFragment()
        val fragment3 = SitesListFragment()
        fragmentList.add(fragment1)
        fragmentList.add(fragment2)
        fragmentList.add(fragment3)
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            val titles = arrayOf("Topics", "News", "Sites")
            override fun getItem(position: Int) = fragmentList[position]

            override fun getCount() = fragmentList.size

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
        val imgAvatar = navigation.getHeaderView(0).find<ImageView>(R.id.imgAvatar)
        val tvUserName = navigation.getHeaderView(0).find<TextView>(R.id.tvUserName)
        if (mDiycode.isLogin) {
            val me = mCache.me
            if (me != null) {
                Glide.with(this).load(me.avatar_url).into(imgAvatar)
                tvUserName.text = me.login
                imgAvatar.onClick {

                    startActivity<UserActivity>("user" to User().apply {
                        id=me.id
                        login=me.login
                        name=me.name
                        avatar_url=me.avatar_url
                    })
                }
                return
            } else {
                mDiycode.me
            }
        } else {
            mCache.removeMe()
            imgAvatar.setImageResource(R.mipmap.ic_launcher)
            tvUserName.text = "点击头像登录"
            imgAvatar.onClick {
                startActivity<LoginActivity>()
            }
        }

    }


    fun quickToTop() {
        when (mCurrentPosition) {
            0 -> {
                (fragmentList[0] as TopicListFragment).quickToTop()
            }
            1 -> {
                (fragmentList[1] as NewsListFragment).quickToTop()
            }
            2 -> {
                (fragmentList[2] as SitesListFragment).quickToTop()
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginResponse(event: GetMeEvent) {
        if (event.isOk) {
            mCache.saveMe(event.bean)
            loadMenuData()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginOutResponse(event: LogoutEvent) {
        if (event.isOk) {
            loadMenuData()
        }
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
