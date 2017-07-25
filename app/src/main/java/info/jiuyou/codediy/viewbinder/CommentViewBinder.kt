package info.jiuyou.codediy.viewbinder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import info.jiuyou.codediy.R
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/25 0025  11:35
 * des ：
 */
class CommentViewBinder(val ctx: Context, val block: (String) -> Unit) : ItemViewBinder<CommentViewBinder.Comment, BaseViewHolder>() {
    private var etText: EditText? = null
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseViewHolder {
        return BaseViewHolder(inflater.inflate(R.layout.item_reply_comment, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, item: CommentViewBinder.Comment) {
        etText = holder.getView<EditText>(R.id.et_text)
        holder.getView<Button>(R.id.btn_comment).onClick {
            val msg = etText?.text.toString().trim()
            if (msg.isBlank()) {
                ctx.toast("请先填写评论内容")
                return@onClick
            }
            block(msg)
        }
    }

    fun clear() {
        etText?.setText("")
    }

    class Comment
}
