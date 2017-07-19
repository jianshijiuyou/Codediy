package info.jiuyou.codediy.base.app

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.jetbrains.anko.AnkoLogger

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/19 0019  9:46
 * des ：
 */
class ViewHolder(layoutInflater: LayoutInflater, viewParent: ViewGroup?, layoutResId: Int) : AnkoLogger {
    val rootView = layoutInflater.inflate(layoutResId, viewParent, false)
    val views = SparseArray<View>()

    operator fun get(id: Int): View? {
        var view = views[id]
        if (view == null) {
            view = rootView.findViewById(id)
            views[id] = view
        }
        return view
    }

    operator fun <T> SparseArray<T>.set(id: Int, value: T) {
        this.put(id, value)
    }


    fun setText(id: Int, text: CharSequence) {
        (this[id] as TextView).text = text
    }

    fun loadImage(ctx: Context, src: String, id: Int) {
        val url = if (src.contains("diycode")) src.replace("large_avatar", "avatar") else src
        Glide.with(ctx).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(this[id] as ImageView)
    }

    fun setOnClickListener(listener: View.OnClickListener, vararg ids: Int) {
        ids.forEach { this@ViewHolder[it]?.setOnClickListener(listener) }
    }
}

