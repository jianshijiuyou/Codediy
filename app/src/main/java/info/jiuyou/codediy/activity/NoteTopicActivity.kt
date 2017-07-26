package info.jiuyou.codediy.activity

import info.jiuyou.codediy.R
import info.jiuyou.codediy.base.app.BaseActivity
import info.jiuyou.codediy.fragment.NodeTopicListFragment

class NoteTopicActivity : BaseActivity() {

    private var nodeId = 0
    private lateinit var nodeName: String

    override fun getLayoutId() = R.layout.activity_note_topic

    override fun initData() {

        nodeId = intent.getIntExtra("nodeId", 0)
        nodeName = intent.getStringExtra("nodeName")
    }


    override fun initViews() {
        title = nodeName
        supportFragmentManager.beginTransaction().replace(R.id.flContent, NodeTopicListFragment.newInstance(nodeId)).commit()
    }

}
