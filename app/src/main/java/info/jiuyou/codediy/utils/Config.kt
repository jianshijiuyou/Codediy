package info.jiuyou.codediy.utils

import android.content.Context
import android.util.LruCache
import com.gcssloop.diycode_sdk.utils.ACache
import java.io.Serializable

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：17-7-18  下午10:02
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
class Config private constructor(context: Context) {
    val mDiskCache = ACache.get(context, "config")!!


    companion object {
        val M = 1024 * 1024
        var mConfig: Config? = null
        val mLruCache = LruCache<String, Any>(1 * M)
        val Key_Browser = "UseInsideBrowser_"
        val Key_MainViewPager_Position = "Key_MainViewPager_Position"
        val Key_TopicList_LastPosition = "Key_TopicList_LastPosition"
        val Key_TopicList_LastOffset = "Key_TopicList_LastOffset"
        val Key_TopicList_PageIndex = "Key_TopicList_PageIndex"
        val Key_NewsList_LastScroll = "Key_NewsList_LastScroll"
        val Key_NewsList_LastPosition = "Key_NewsList_LastPosition"
        val Key_NewsList_PageIndex = "Key_NewsList_PageIndex"


        fun init(ctx: Context): Config {
            if (mConfig == null) {
                synchronized(Config::class.java) {
                    if (mConfig == null) {
                        mConfig = Config(ctx)
                    }
                }
            }
            return mConfig!!
        }

        fun instance() = mConfig
    }

    //--- 基础 -----------------------------------------------------------------------------------
    fun <T : Serializable> saveData(key: String, value: T) {
        mLruCache.put(key, value)
        mDiskCache.put(key, value)
    }

    fun <T : Serializable> getData(key: String, def: T): T {
        var value = mLruCache[key] as T
        if (value != null) {
            return value
        }
        value = mDiskCache.getAsObject(key) as T
        if (value != null) {
            return value
        }
        return def
    }

    //--- 浏览器 ---------------------------------------------------------------------------------
    fun setUesInsideBrowser(bool: Boolean) {
        saveData(Key_Browser, bool)
    }

    fun isUseInsideBrowser() = getData(Key_Browser, true)

    //--- 首页状态 -------------------------------------------------------------------------------

    fun saveMainViewPagerPosition(position: Int) {
        saveData(Key_MainViewPager_Position, position)
    }

    fun getMainViewPagerPosition() = getData(Key_MainViewPager_Position, 0)

    //--- Topic状态 ------------------------------------------------------------------------------
    fun saveTopicListState(lastPosition: Int, lastOffset: Int) {
        saveData(Key_TopicList_LastPosition, lastPosition)
        saveData(Key_TopicList_LastOffset, lastOffset)
    }

    fun getTopicListLastPosition() = getData(Key_TopicList_LastPosition, 0)
    fun getTopicListLastOffset() = getData(Key_TopicList_LastOffset, 0)

    fun saveTopicListPageIndex(pageIndex: Int) {
        saveData(Key_TopicList_PageIndex, pageIndex)
    }

    fun getTopicListPageIndex() = getData(Key_TopicList_PageIndex, 0)
    //--- News状态 ------------------------------------------------------------------------------
    fun saveNewsListScroll(lastScrollY: Int) {
        saveData(Key_NewsList_LastScroll, lastScrollY)
    }

    fun getNewsLastScroll() = getData(Key_NewsList_LastScroll, 0)

    fun saveNewsListPosition(lastPosition: Int) {
        saveData(Key_NewsList_LastPosition, lastPosition)
    }

    fun getNewsListLastPosition() = getData(Key_NewsList_LastPosition, 0)

    fun saveNewsListPageIndex(pageIndex: Int) {
        saveData(Key_NewsList_PageIndex, pageIndex)
    }

    fun getNewsListPageIndex() = getData(Key_NewsList_PageIndex, 0)
}
