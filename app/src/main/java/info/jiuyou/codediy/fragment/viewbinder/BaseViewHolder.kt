package info.jiuyou.codediy.fragment.viewbinder

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import org.jetbrains.anko.find

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/21 0021  16:42
 * des ：
 */
class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val views = SparseArray<View>()

    inline fun <reified T : View> getView(id: Int): T {
        var view = views[id]

        if (view == null) {
            view = itemView.find<T>(id)
            views.put(id, view)
            return view
        }
        return view as T
    }
}