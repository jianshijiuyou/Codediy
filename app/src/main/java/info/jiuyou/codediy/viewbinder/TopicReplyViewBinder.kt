package info.jiuyou.codediy.viewbinder

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.gcssloop.diycode_sdk.api.topic.bean.TopicReply
import info.jiuyou.codediy.R
import info.jiuyou.codediy.base.glide.GlideImageGetter
import info.jiuyou.codediy.utils.HtmlUtil
import info.jiuyou.codediy.utils.ImageUtils
import info.jiuyou.codediy.utils.TimeUtil
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/25 0025  9:38
 * des ：
 */
class TopicReplyViewBinder(val ctx: Context) : ItemViewBinder<TopicReply, BaseViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseViewHolder {
        return BaseViewHolder(inflater.inflate(R.layout.item_topic, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, item: TopicReply) {
        ImageUtils.loadImage(ctx, item.user.avatar_url, holder.getView<ImageView>(R.id.img_head))
        holder.getView<TextView>(R.id.tv_name).text = item.user.login
        holder.getView<TextView>(R.id.tv_note).visibility = View.GONE
        holder.getView<TextView>(R.id.tv_time).text = TimeUtil.computePastTime(item.updated_at)
        val content = holder.getView<TextView>(R.id.tv_title)
        content.text = Html.fromHtml(HtmlUtil.removeP(item.body_html), GlideImageGetter(ctx, content), null)
        holder.getView<TextView>(R.id.tv_subtitle).visibility= View.GONE

        holder.getView<ImageView>(R.id.img_head).onClick {
            ctx.toast(item.user.login)
        }
        holder.getView<TextView>(R.id.tv_name).onClick {
            ctx.toast(item.user.login)
        }
    }

}