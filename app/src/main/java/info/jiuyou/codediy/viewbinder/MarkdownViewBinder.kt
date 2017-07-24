package info.jiuyou.codediy.viewbinder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.zzhoujay.richtext.RichText
import info.jiuyou.codediy.R
import me.drakeet.multitype.ItemViewBinder

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/24 0024  16:39
 * des ：
 */
class MarkdownViewBinder(val ctx: Context) : ItemViewBinder<String, BaseViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseViewHolder {
        return BaseViewHolder(inflater.inflate(R.layout.item_markdown, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, item: String) {
        val content = holder.getView<TextView>(R.id.tv_content)
        RichText.fromMarkdown(item).into(content)
    }

}