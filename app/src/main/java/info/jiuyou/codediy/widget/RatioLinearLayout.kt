package info.jiuyou.codediy.widget

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import info.jiuyou.codediy.R


/**
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/19 0019  14:02
 * des ：
 */
class RatioLinearLayout(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : this(context, attrs)

    private var mRatio: Float = 0F

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.RatioLinearLayout, 0, 0)
        val ratioString = a.getString(R.styleable.RatioLinearLayout_ratio)
        a.recycle()

        if (!TextUtils.isEmpty(ratioString)) {
            val numbers = ratioString.split(":")
            if (numbers.size != 2 || !TextUtils.isDigitsOnly(numbers[0])
                    || !TextUtils.isDigitsOnly(numbers[1])) {
                throw  IllegalArgumentException(
                        "app:ratio should be a string in the format \"<width>:<height>\": "
                                + ratioString)
            }
            mRatio = Integer.parseInt(numbers[0]) / Integer.parseInt(numbers[1]).toFloat()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (mRatio > 0) {
            var width: Int
            var height: Int
            if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.EXACTLY) {
                height = measuredHeight
                width = Math.round(mRatio * height)
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    width = Math.max(width, minimumWidth)
                }
            } else {
                width = measuredWidth
                height = Math.round(width / mRatio)
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    height = Math.max(height, minimumHeight)
                }
            }

            setMeasuredDimension(width, height)
        }
    }
}