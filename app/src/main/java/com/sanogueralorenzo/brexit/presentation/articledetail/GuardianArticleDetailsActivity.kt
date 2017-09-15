package com.sanogueralorenzo.brexit.presentation.articledetail

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import com.sanogueralorenzo.brexit.R
import com.sanogueralorenzo.brexit.presentation.*
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.article.ArticleItem
import kotlinx.android.synthetic.main.activity_guardian_article_details.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import javax.inject.Inject

class GuardianArticleDetailsActivity : AppCompatActivity(), GuardianArticleDetailsView {

    @Inject
    lateinit var presenterFactory: GuardianArticleDetailsPresenter.Factory

    private val presenter: GuardianArticleDetailsPresenter by lazy { presenterFactory.create(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guardian_article_details)
        (application as App).injector?.inject(this)
        init()
    }

    override val articleItem: ArticleItem by lazy{ intent.extras.get("ARTICLE") as ArticleItem }

    override fun init() {
        articleDetailsToolbar.setNavigationOnClickListener { onBackPressed() }
        articleDetailsFavoriteImageButton.onClick { presenter.addDeleteFavorite() }
        articleDetailsAppBarLayout.addCollapsingToolbarCollapsedTitle(articleDetailsCollapsingToolbarLayout, getString(R.string.app_name))
        articleDetailsAppBarLayout.removeScrolling()

        articleDetailsImageView.loadUrl(articleItem.url)
        articleDetailsTitleTextView.text = articleItem.title
    }

    override fun setFavoriteIcon(favorite: Boolean) {
        val resource = if (favorite) R.drawable.ic_favorite_24dp else R.drawable.ic_favorite_border_24dp
        articleDetailsFavoriteImageButton.setImageResource(resource)
    }

    override fun favoriteAdded() = toast(resources.getString(R.string.favorite_added))

    override fun favoriteDeleted() = toast(resources.getString(R.string.favorite_deleted))

    override fun addBodyText(body: String) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        articleDetailsBodyTextView.text = Html.fromHtml(body, Html.FROM_HTML_MODE_COMPACT)
    } else {
        articleDetailsBodyTextView.text = Html.fromHtml(body)
    }

    override fun onError() {
        toast(resources.getString(R.string.error))
        onBackPressed()
    }

    override fun onBackPressed() {
        articleDetailsToolbar.gone()
        articleDetailsFavoriteImageButton.gone()
        articleDetailsImageView.gone()
        super.onBackPressed()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }
}
