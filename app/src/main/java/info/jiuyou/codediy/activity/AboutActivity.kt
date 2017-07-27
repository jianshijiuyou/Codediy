package info.jiuyou.codediy.activity

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import info.jiuyou.codediy.BuildConfig
import info.jiuyou.codediy.R
import info.jiuyou.codediy.utils.CustomTabsHelper
import me.drakeet.multitype.Items
import me.drakeet.support.about.*


class AboutActivity : AbsAboutActivity() {
    override fun onCreateTitle() = "关于"

    override fun onCreateHeader(icon: ImageView, slogan: TextView, version: TextView) {

        setNavigationIcon(R.drawable.ic_back_white)
        icon.setImageResource(R.mipmap.ic_launcher)
        slogan.text = "codeDiy"
        version.text = "v${BuildConfig.VERSION_NAME}"
    }

    override fun onItemsCreated(items: Items) {
        items.add(Category("介绍"))
        items.add(Card("本 app 是 diycode 社区的第三方客户端，基于 GcsSloop/diycode-sdk，仿照 GcsSloop/diycode 客户端采用 kotlin 作为主要语言所编写。\n\n编写本项目的初衷在于学习 kotlin 语言，学习大牛的设计思想，编码风格。以及本人对 coding 的热爱。", "项目地址"))
        items.add(Line())
        items.add(Category("Developer"))
        items.add(Contributor(R.drawable.author, "jianshijiuyou", "jianshijiuyou@gmail.com", "https://github.com/jianshijiuyou"))
        items.add(Line())
        items.add(Category("Open Source Licenses"))
        items.add(License("diycode-sdk", "GcsSloop", License.APACHE_2, "https://github.com/GcsSloop/diycode-sdk"))
        items.add(License("MultiType", "drakeet", License.APACHE_2, "https://github.com/drakeet/MultiType"))
        items.add(License("glide", "bumptech", License.APACHE_2, "https://github.com/bumptech/glide"))
        items.add(License("PhotoView", "chrisbanes", License.APACHE_2, "https://github.com/chrisbanes/PhotoView"))
        items.add(License("pageindicatorview", "romandanylyk", License.APACHE_2, "https://github.com/romandanylyk/PageIndicatorView"))
        items.add(License("SmartRefreshLayout", "scwang90", License.APACHE_2, "https://github.com/scwang90/SmartRefreshLayout"))
        items.add(License("leakcanary", "square", License.APACHE_2, "https://github.com/square/leakcanary"))

    }

    override fun onActionClick(action: View?) {
        CustomTabsHelper.openUrl(this,"https://github.com/jianshijiuyou/Codediy")
    }
}
