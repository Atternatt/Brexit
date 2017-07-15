package com.sanogueralorenzo.brexit.presentation.articledetail

import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import android.text.Html
import com.sanogueralorenzo.brexit.R
import com.sanogueralorenzo.brexit.presentation.App
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.article.ArticleItem
import com.sanogueralorenzo.brexit.presentation.gone
import com.sanogueralorenzo.brexit.presentation.loadurl
import kotlinx.android.synthetic.main.activity_guardian_article_details.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class GuardianArticleDetailsActivity : AppCompatActivity(), GuardianArticleDetailsView {

    override fun setFavoriteIcon(favorite: Boolean) {
        val resource = if (favorite) R.drawable.ic_favorite_24dp else R.drawable.ic_favorite_border_24dp
        articleDetailsFavoriteImageView.setImageResource(resource)
    }

    override fun favoriteAdded() {
        toast(resources.getString(R.string.favorite_added))
    }

    override fun favoriteDeleted() {
        toast(resources.getString(R.string.favorite_deleted))
    }

    override fun addBodyText(body: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            articleDetailsBodyTextView.text = Html.fromHtml(body, Html.FROM_HTML_MODE_COMPACT)
        } else {
            articleDetailsBodyTextView.text = Html.fromHtml(body)
        }
    }

    override fun onError() {
        toast(resources.getString(R.string.error))
        onBackPressed()
    }

    @Inject
    lateinit var guardianArticleDetailsPresenter: GuardianArticleDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guardian_article_details)

        (application as App).injector?.inject(this)

        val article = intent.extras.get("ARTICLE") as ArticleItem

        articleDetailsToolbar.setNavigationOnClickListener { onBackPressed() }
        articleDetailsFavoriteImageView.setOnClickListener { guardianArticleDetailsPresenter.saveFavorite(article.id!!) }
        articleDetailsImageView.loadurl(article.url)
        articleDetailsTitleTextView.text = article.title

        guardianArticleDetailsPresenter.attachView(this)
        guardianArticleDetailsPresenter.getCacheArticle(article.id!!)
        guardianArticleDetailsPresenter.getArticle(article.id)
        guardianArticleDetailsPresenter.checkFavoriteArticle(article.id)

        addTitleOffsetListener()
        removeAppBarLayoutScrolling()
    }

    fun addTitleOffsetListener() {
        articleDetailsAppBarLayout.addOnOffsetChangedListener({ appBarLayout1, verticalOffset ->
            val percentage = Math.abs(verticalOffset).toFloat() / appBarLayout1.getTotalScrollRange()
            if (percentage.toInt() != 0) {
                articleDetailsCollapsingToolbarLayout.title = resources.getString(R.string.app_name)
            } else {
                articleDetailsCollapsingToolbarLayout.title = " "
            }
        })
    }

    fun removeAppBarLayoutScrolling() {
        val params = articleDetailsAppBarLayout.getLayoutParams() as CoordinatorLayout.LayoutParams
        params.behavior = AppBarLayout.Behavior()
        val behavior = params.behavior as AppBarLayout.Behavior
        behavior.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                return false
            }
        })
    }

    override fun onBackPressed() {
        articleDetailsToolbar.gone()
        articleDetailsFavoriteImageView.gone()
        articleDetailsImageView.gone()
        super.onBackPressed()
    }

    override fun onDestroy() {
        guardianArticleDetailsPresenter.dispose()
        guardianArticleDetailsPresenter.detachView()
        super.onDestroy()
    }
}
