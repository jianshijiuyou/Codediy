package info.jiuyou.codediy.viewbinder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import info.jiuyou.codediy.R
import info.jiuyou.codediy.activity.LoginActivity
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/25 0025  11:35
 * des ：
 */
class LoginViewBinder(val ctx: Context) : ItemViewBinder<LoginViewBinder.Login, BaseViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseViewHolder {
        return BaseViewHolder(inflater.inflate(R.layout.item_reply_login, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, item: LoginViewBinder.Login) {
        holder.getView<Button>(R.id.btn_login).onClick {
            ctx.startActivity<LoginActivity>()
        }
    }

    class Login
}
