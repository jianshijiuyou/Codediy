package info.jiuyou.codediy.viewbinder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import info.jiuyou.codediy.R
import info.jiuyou.codediy.base.webview.GcsMarkdownViewClient
import info.jiuyou.codediy.base.webview.WebImageListener
import info.jiuyou.codediy.widget.MarkdownView
import me.drakeet.multitype.ItemViewBinder

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/24 0024  16:39
 * des ：
 */
class MarkdownViewBinder(val ctx: Context) : ItemViewBinder<String, BaseViewHolder>() {
    private var mMarkdownView: MarkdownView? = null
    private var isFirst = true
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseViewHolder {
        val holder = BaseViewHolder(inflater.inflate(R.layout.item_markdown, parent, false))
        val content = holder.getView<FrameLayout>(R.id.content)
        //1
        mMarkdownView = MarkdownView(ctx)
        mMarkdownView?.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        content.addView(mMarkdownView)
        val listener = WebImageListener(ctx)
        mMarkdownView?.addJavascriptInterface(listener, "listener")
        val mWebViewClient = GcsMarkdownViewClient(ctx)
        mMarkdownView?.setWebViewClient(mWebViewClient)
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder, item: String) {
        //2
        if (isFirst) {
            mMarkdownView?.setMarkDownText(item)
            isFirst = false
        }

    }

    // 防止 WebView 引起的内存泄漏
    fun clearWebViewResource() {
        if (mMarkdownView != null) {
            mMarkdownView?.clearHistory()
            (mMarkdownView?.parent as ViewGroup).removeView(mMarkdownView)
            mMarkdownView?.tag = null
            mMarkdownView?.loadUrl("about:blank")
            mMarkdownView?.stopLoading()
            mMarkdownView?.setWebViewClient(null)
            mMarkdownView?.setWebChromeClient(null)
            mMarkdownView?.removeAllViews()
            mMarkdownView?.destroy()
            mMarkdownView = null
        }
    }
}