package info.jiuyou.codediy.base.webview

import android.content.Context
import android.webkit.JavascriptInterface
import com.gcssloop.diycode_sdk.log.Logger
import info.jiuyou.codediy.activity.ImageActivity
import info.jiuyou.codediy.utils.UrlUtil
import org.jetbrains.anko.startActivity

class WebImageListener(val mContext: Context) {
    val images = mutableListOf<String>()

    /**
     * 收集图片(当发现图片时会调用该方法)

     * @param url 图片链接
     */
    @JavascriptInterface
    fun collectImage(url: String) {
        Logger.e("collect:" + url)
        if (UrlUtil.isGifSuffix(url)) {
            return
        }
        if (!images.contains(url))
            images.add(url)
    }

    /**
     * 图片被点击(图片被点击时调用该方法)

     * @param url 图片链接
     */
    @JavascriptInterface
    fun onImageClicked(url: String) {
        Logger.e("clicked:" + url)
        mContext.startActivity<ImageActivity>("url" to url, "images" to images)
    }

}
