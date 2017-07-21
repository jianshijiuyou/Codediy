package info.jiuyou.codediy.fragment.viewbinder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.gcssloop.diycode_sdk.api.news.bean.New
import info.jiuyou.codediy.R
import info.jiuyou.codediy.utils.ImageUtils
import info.jiuyou.codediy.utils.TimeUtil
import me.drakeet.multitype.ItemViewBinder

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/21 0021  16:02
 * des ：
 */
class NewsViewBinder(val ctx: Context) : ItemViewBinder<New, BaseViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseViewHolder {
        return BaseViewHolder(inflater.inflate(R.layout.item_topic, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, item: New) {
        ImageUtils.loadImage(ctx, item.user.avatar_url, holder.getView<ImageView>(R.id.img_head))
        holder.getView<TextView>(R.id.tv_name).text = item.user.name
        holder.getView<TextView>(R.id.tv_note).text = item.node_name
        holder.getView<TextView>(R.id.tv_time).text = TimeUtil.computePastTime(item.updated_at)
        holder.getView<TextView>(R.id.tv_title).text = item.title
        holder.getView<TextView>(R.id.tv_subtitle).text = item.address
    }
}