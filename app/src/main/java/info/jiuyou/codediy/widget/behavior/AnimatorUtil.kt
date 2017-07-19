package info.jiuyou.codediy.widget.behavior

import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.view.View
import android.view.animation.AccelerateInterpolator

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：17-7-19  下午10:19
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
object AnimatorUtil {
    private val FAST_OUT_SLOW_IN_INTERPOLATOR = LinearOutSlowInInterpolator()

    // 显示view
    fun translateShow(view: View, viewPropertyAnimatorListener: ViewPropertyAnimatorListener) {
        view.visibility = View.VISIBLE
        ViewCompat.animate(view)
                .translationY(0f)
                .setDuration(400)
                .setListener(viewPropertyAnimatorListener)
                .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                .start()
    }

    // 隐藏view
    fun translateHide(view: View, viewPropertyAnimatorListener: ViewPropertyAnimatorListener) {
        view.visibility = View.VISIBLE
        ViewCompat.animate(view)
                .translationY(260f)
                .setDuration(400)
                .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                .setListener(viewPropertyAnimatorListener)
                .start()
    }
}