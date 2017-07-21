package info.jiuyou.codediy.fragment.viewbinder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.gcssloop.diycode_sdk.api.sites.bean.Sites
import info.jiuyou.codediy.R
import me.drakeet.multitype.ItemViewBinder

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/21 0021  17:33
 * des ：
 */
class SiteViewBinder(val ctx: Context) : ItemViewBinder<Sites.Site, BaseViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseViewHolder {
        return BaseViewHolder(inflater.inflate(R.layout.item_site, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, item: Sites.Site) {
        holder.getView<TextView>(R.id.tv_name).text=item.name
    }
}