package info.jiuyou.codediy.viewbinder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.gcssloop.diycode_sdk.api.sites.bean.Sites
import info.jiuyou.codediy.R
import me.drakeet.multitype.ItemViewBinder

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/21 0021  17:32
 * des ：
 */
class SitesViewBinder(val ctx: Context) : ItemViewBinder<Sites, BaseViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseViewHolder {
        return BaseViewHolder(inflater.inflate(R.layout.item_sites, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, item: Sites) {
        holder.getView<TextView>(R.id.tv_name).text=item.name
    }
}