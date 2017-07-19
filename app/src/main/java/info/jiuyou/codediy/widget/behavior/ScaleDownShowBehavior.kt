package info.jiuyou.codediy.widget.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.util.AttributeSet
import android.view.View


/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：17-7-19  下午9:37
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
class ScaleDownShowBehavior(ctx: Context, attributeSet: AttributeSet) : FloatingActionButton.Behavior(ctx, attributeSet) {

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?,
                                     child: FloatingActionButton?, directTargetChild: View?,
                                     target: View?, nestedScrollAxes: Int): Boolean {
        if (nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL) {
            return true
        }
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild,
                target, nestedScrollAxes)
    }

    private var isAnimateIng = false   // 是否正在动画
    private var isShow = true  // 是否已经显示

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton,
                                target: View?, dxConsumed: Int, dyConsumed: Int,
                                dxUnconsumed: Int, dyUnconsumed: Int) {
        if ((dyConsumed > 0 || dyUnconsumed > 0) && !isAnimateIng && isShow) {// 手指上滑，隐藏FAB
            AnimatorUtil.translateHide(child, object : StateListener() {
                override fun onAnimationStart(view: View) {
                    super.onAnimationStart(view)
                    isShow = false
                }
            })
        } else if (dyConsumed < 0 || dyUnconsumed < 0 && !isAnimateIng && !isShow) {
            AnimatorUtil.translateShow(child, object : StateListener() {
                override fun onAnimationStart(view: View) {
                    super.onAnimationStart(view)
                    isShow = true
                }
            })// 手指下滑，显示FAB
        }
    }

    open inner class StateListener : ViewPropertyAnimatorListener {
        override fun onAnimationStart(view: View) {
            isAnimateIng = true
        }

        override fun onAnimationEnd(view: View) {
            isAnimateIng = false
        }

        override fun onAnimationCancel(view: View) {
            isAnimateIng = false
        }
    }
}