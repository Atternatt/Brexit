package com.sanogueralorenzo.brexit.presentation.articlelist

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.sanogueralorenzo.brexit.presentation.articledetail.GuardianArticleDetailsActivity
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.GuardianAdapter
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.article.ArticleItem
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.article.OnArticleClickListener
import com.sanogueralorenzo.brexit.R
import com.sanogueralorenzo.brexit.presentation.App
import com.sanogueralorenzo.brexit.presentation.commons.adapter.ViewType
import kotlinx.android.synthetic.main.activity_guardian_article_list.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class GuardianArticleListActivity : AppCompatActivity(), GuardianArticleListView, OnArticleClickListener {

    override fun onArticleClick(item: ArticleItem) {
        if (TextUtils.isEmpty(item.id)) {
            toast(resources.getString(R.string.error_empty_article))
            return
        }
        val intent = Intent(this@GuardianArticleListActivity, GuardianArticleDetailsActivity::class.java)
        intent.putExtra("ARTICLE", item)
        val sharedView = articleListAppBarLayout
        val transitionName = getString(R.string.toolbar)
        val transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this, sharedView, transitionName)
        startActivity(intent, transitionActivityOptions.toBundle())
    }

    override fun addItemList(itemList: List<ViewType>) {
        articleListSwipeRefreshLayout.isRefreshing = false
        (articleListRecyclerView.adapter as GuardianAdapter).clear()
        (articleListRecyclerView.adapter as GuardianAdapter).addItemList(itemList)
        (articleListRecyclerView.adapter as GuardianAdapter).notifyDataSetChanged()
    }

    override fun onError() {
        articleListSwipeRefreshLayout.isRefreshing = false
        toast(resources.getString(R.string.error))
    }

    @Inject
    lateinit var guardianArticleListPresenter: GuardianArticleListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guardian_article_list)

        (application as App).injector?.inject(this)

        initRv()
        initAdapter()
        initSwipeRefreshLayout()
    }

    fun initRv() {
        articleListRecyclerView.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
        }
    }

    fun initAdapter() {
        articleListRecyclerView.adapter = GuardianAdapter(this)
    }

    fun initSwipeRefreshLayout() {
        articleListSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW)
        articleListSwipeRefreshLayout.setOnRefreshListener({ guardianArticleListPresenter.getArticleList() })
        articleListSwipeRefreshLayout.isRefreshing = true
    }

    override fun onStart() {
        super.onStart()
        guardianArticleListPresenter.attachView(this)
        guardianArticleListPresenter.getCacheArticleList()
        guardianArticleListPresenter.getArticleList()
    }

    override fun onStop() {
        guardianArticleListPresenter.dispose()
        guardianArticleListPresenter.detachView()
        super.onStop()
    }
}
