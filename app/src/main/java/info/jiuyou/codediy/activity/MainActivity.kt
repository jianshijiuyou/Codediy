package info.jiuyou.codediy.activity

import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.ArrayAdapter
import info.jiuyou.codediy.R
import info.jiuyou.codediy.base.app.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast


class MainActivity : BaseActivity(), AnkoLogger, NavigationView.OnNavigationItemSelectedListener {

    private var isToolbarFirstClick = true


    override fun getLayoutId() = R.layout.activity_main

    override fun initData() {

    }

    override fun initViews() {

        initMenu()

//        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayOf("item", "item", "item", "item", "item", "item", "item", "item", "item", "item", "item", "item", "item", "item", "item", "item", "item", "item", "item", "item", "item", "item", "item"))
//        listView.adapter = adapter

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
            }
            R.id.action_settings -> {
                toast("设置")
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}
