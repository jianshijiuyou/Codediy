package info.jiuyou.codediy.viewbinder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.gcssloop.diycode_sdk.api.topic.bean.Topic
import info.jiuyou.codediy.R
import info.jiuyou.codediy.activity.NoteTopicActivity
import info.jiuyou.codediy.activity.TopicContentActivity
import info.jiuyou.codediy.activity.UserActivity
import info.jiuyou.codediy.utils.ImageUtils
import info.jiuyou.codediy.utils.TimeUtil
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/20 0020  17:26
 * des ：
 */
class TopicViewBinder(val ctx: Context) : ItemViewBinder<Topic, TopicViewBinder.ViewHolder>() {

    private var isClick = true
    private var showNode = true
    private var showHead = true

    constructor(ctx: Context, isClick: Boolean, showNode: Boolean = true, showHead: Boolean = true) : this(ctx) {
        this.isClick = isClick
        this.showNode = showNode
        this.showHead = showHead
    }


    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_topic, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Topic) {

        holder.apply {
            tvTitle.text = item.title
            name.text = item.user.login
            if (showNode) {
                note.text = item.node_name
            } else {
                note.visibility = View.INVISIBLE
            }
            headLayout.visibility = if (showHead) View.VISIBLE else View.GONE

            time.text = TimeUtil.computePastTime(item.updated_at)
            subTitle.text = "评论 ${item.replies_count} 条"
            ImageUtils.loadImage(ctx, item.user.avatar_url, imgHead)

            name.onClick {
                ctx.startActivity<UserActivity>("user" to item.user)
            }

            imgHead.onClick {
                ctx.startActivity<UserActivity>("user" to item.user)
            }

            if (showNode) {
                note.onClick {
                    ctx.startActivity<NoteTopicActivity>("nodeId" to item.node_id, "nodeName" to item.node_name)
                }
            }

            if (isClick) {
                itemRoot.onClick {
                    ctx.startActivity<TopicContentActivity>("topic" to item)
                }
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.find(R.id.tv_title)
        val imgHead: ImageView = itemView.find(R.id.img_head)
        val name: TextView = itemView.find(R.id.tv_name)
        val note: TextView = itemView.find(R.id.tv_note)
        val time: TextView = itemView.find(R.id.tv_time)
        val subTitle: TextView = itemView.find(R.id.tv_subtitle)
        val itemRoot: View = itemView.find(R.id.item)
        val headLayout: View = itemView.find(R.id.head_layout)
    }
}