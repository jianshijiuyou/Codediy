package info.jiuyou.codediy.activity

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import info.jiuyou.codediy.R
import info.jiuyou.codediy.base.app.BaseActivity
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : BaseActivity() {

    private lateinit var url: String
    private lateinit var images: MutableList<String>
    private var curPos = 0
    override fun getLayoutId() = R.layout.activity_image

    override fun initData() {
        url = intent.getStringExtra("url")
        images = intent.getStringArrayListExtra("images")
        title = "查看图片"

        curPos = images.indexOf(url)
    }

    override fun initViews() {
        viewPager.adapter = object : PagerAdapter() {

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val img = PhotoView(this@ImageActivity)
                img.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                Glide.with(this@ImageActivity).load(images[position]).into(img)
                container.addView(img)
                return img
            }

            override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
                container.removeView(obj as View)
            }


            override fun isViewFromObject(view: View?, `object`: Any?) = view == `object`

            override fun getCount() = images.size

        }

        viewPager.currentItem = curPos
        pageIndicatorView.selection = curPos

    }


}
