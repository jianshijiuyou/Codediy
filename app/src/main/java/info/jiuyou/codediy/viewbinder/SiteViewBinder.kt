package info.jiuyou.codediy.viewbinder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.gcssloop.diycode_sdk.api.sites.bean.Sites
import info.jiuyou.codediy.R
import info.jiuyou.codediy.utils.CustomTabsHelper
import info.jiuyou.codediy.utils.ImageUtils
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.sdk25.coroutines.onClick

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
        holder.getView<TextView>(R.id.tv_name).text = item.name
        ImageUtils.loadImage(ctx, item.avatar_url, holder.getView<ImageView>(R.id.img_logo))

        holder.getView<View>(R.id.item).onClick {
            CustomTabsHelper.openUrl(ctx, item.url)
        }
    }
}