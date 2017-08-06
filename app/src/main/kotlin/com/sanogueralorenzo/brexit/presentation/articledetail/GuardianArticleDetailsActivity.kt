package com.sanogueralorenzo.brexit.presentation.articledetail

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import com.sanogueralorenzo.brexit.R
import com.sanogueralorenzo.brexit.presentation.*
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.article.ArticleItem
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
    lateinit var presenter: GuardianArticleDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guardian_article_details)

        (application as App).injector?.inject(this)

        val article = intent.extras.get("ARTICLE") as ArticleItem

        articleDetailsToolbar.setNavigationOnClickListener { onBackPressed() }
        articleDetailsFavoriteImageView.setOnClickListener { presenter.saveFavorite(article.id!!) }
        articleDetailsImageView.loadUrl(article.url)
        articleDetailsTitleTextView.text = article.title

        presenter.attachView(this)
        presenter.getCacheArticle(article.id!!)
        presenter.getArticle(article.id)
        presenter.checkFavoriteArticle(article.id)

        addTitleOffsetListener()
        removeAppBarLayoutScrolling()
    }

    fun addTitleOffsetListener() {
        articleDetailsAppBarLayout.addCollapsingToolbarCollapsedTitle(articleDetailsCollapsingToolbarLayout, getString(R.string.app_name))
    }

    fun removeAppBarLayoutScrolling() {
        articleDetailsAppBarLayout.removeScrolling()
    }

    override fun onBackPressed() {
        articleDetailsToolbar.gone()
        articleDetailsFavoriteImageView.gone()
        articleDetailsImageView.gone()
        super.onBackPressed()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}
