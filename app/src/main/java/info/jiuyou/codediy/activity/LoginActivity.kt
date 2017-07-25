package info.jiuyou.codediy.activity

import android.app.ProgressDialog
import com.gcssloop.diycode_sdk.api.login.event.LoginEvent
import info.jiuyou.codediy.R
import info.jiuyou.codediy.base.app.BaseActivity
import info.jiuyou.codediy.utils.CustomTabsHelper
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

class LoginActivity : BaseActivity() {

    private lateinit var dialog:ProgressDialog

    override fun getLayoutId() = R.layout.activity_login
    override fun initViews() {
        title = "登录"
        btnLogin.onClick {
            val name = username.text.toString().trim()
            val pwd = password.text.toString().trim()

            if (name.isBlank() || pwd.isBlank()) {
                toast("用户名和密码不能为空")
                return@onClick
            }
            dialog=indeterminateProgressDialog(message = "正在登录…")
            mDiycode.login(name, pwd)
        }

        tvRegister.onClick {
            CustomTabsHelper.openUrl(this@LoginActivity, "https://www.diycode.cc/account/sign_up")
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginResponse(event: LoginEvent) {
        dialog.dismiss()
        if (event.isOk) {
            mDiycode.me
            finish()
        } else {
            toast("登录失败:${event.codeDescribe}")
        }
    }


    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}
