package info.jiuyou.codediy.fragment.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gcssloop.diycode_sdk.api.topic.bean.Topic
import me.drakeet.multitype.ItemViewBinder

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/20 0020  17:26
 * des ：
 */
class TopicViewBinder : ItemViewBinder<Topic, TopicViewBinder.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Topic) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

    }
}