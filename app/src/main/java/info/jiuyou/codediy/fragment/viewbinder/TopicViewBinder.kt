package info.jiuyou.codediy.fragment.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gcssloop.diycode_sdk.api.topic.bean.Topic
import info.jiuyou.codediy.R
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.find

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/20 0020  17:26
 * des ：
 */
class TopicViewBinder : ItemViewBinder<String, TopicViewBinder.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_topic,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: String) {
        holder.tvTitle.text=item
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle:TextView=itemView.find(R.id.tv_title)
    }
}