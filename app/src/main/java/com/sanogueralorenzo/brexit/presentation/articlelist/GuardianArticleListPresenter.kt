package com.sanogueralorenzo.brexit.presentation.articlelist

import com.sanogueralorenzo.brexit.data.model.Result
import com.sanogueralorenzo.brexit.domain.usecases.ArticlesFavoriteArticleIdsLists
import com.sanogueralorenzo.brexit.domain.usecases.CombineArticleListFavoriteArticleUrlListUseCase
import com.sanogueralorenzo.brexit.presentation.IView
import com.sanogueralorenzo.brexit.presentation.Presenter
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.header.HeaderItem
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.WeekItem
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.article.ArticleItem
import com.sanogueralorenzo.brexit.presentation.commons.ViewType
import com.sanogueralorenzo.brexit.presentation.isNewWeek
import com.sanogueralorenzo.brexit.presentation.weekAgoFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

interface GuardianArticleListView : IView {
    fun addItemList(itemList: List<ViewType>)
    fun onError()
}

class GuardianArticleListPresenter
@Inject constructor
(private val useCase: CombineArticleListFavoriteArticleUrlListUseCase,
 private val mapper: ArticlesFavoriteArticleIdsListsToViewTypeMapper)
    : Presenter<GuardianArticleListView>() {

    override fun attachView(view: GuardianArticleListView) {
        super.attachView(view)
        getArticleList()
    }

    fun getArticleList() {
        addDisposable(useCase.execute()
                .subscribe({ view?.addItemList(mapper.map(it)) },
                        { view?.onError() }))
    }
}

class ArticlesFavoriteArticleIdsListsToViewTypeMapper @Inject constructor() {

    fun map(lists: ArticlesFavoriteArticleIdsLists): List<ViewType> {
        val itemList = ArrayList<ViewType>()
        val resultList = ArrayList(lists.articleList)
        if (!lists.favoriteArticleIdList.isEmpty()) {
            addFavoriteArticles(resultList, lists.favoriteArticleIdList, itemList)
        }
        addArticles(resultList, itemList)
        return itemList
    }

    private fun addFavoriteArticles(resultList: ArrayList<Result>, favoriteArticleUrlList: List<String>, itemList: ArrayList<ViewType>) {
        val favoriteArticleList = ArrayList<ArticleItem>()
        //Filter favorites, remove them from the result list and add them to the  itemList
        resultList
                .filter { favoriteArticleUrlList.contains(it.apiUrl) }
                .forEach {
                    favoriteArticleList.add(createArticleItem(it))
                    resultList.remove(it)
                }
        if (!favoriteArticleList.isEmpty()) {
            itemList.add(createHeaderItem("Favorites"))
            itemList.add(createWeekItem(favoriteArticleList))
        }
    }

    private fun addArticles(resultList: ArrayList<Result>, itemList: ArrayList<ViewType>) {
        val articleList = ArrayList<ArticleItem>()
        var previousDate: Date? = null
        for (i in resultList.indices) {
            val result = resultList[i]
            if (i == 0) {
                itemList.add(createHeaderItem(result.webPublicationDate!!.weekAgoFormat()!!))
                articleList.add(createArticleItem(result))
                previousDate = result.webPublicationDate
            } else if (result.webPublicationDate!!.isNewWeek(previousDate!!)) {
                itemList.add(createWeekItem(articleList))
                articleList.clear()
                itemList.add(createHeaderItem(result.webPublicationDate.weekAgoFormat()!!))
                articleList.add(createArticleItem(result))
                previousDate = result.webPublicationDate
                if (i == resultList.size - 1) {
                    itemList.add(createWeekItem(articleList))
                }
            } else if (i == resultList.size - 1) {
                articleList.add(createArticleItem(result))
                itemList.add(createWeekItem(articleList))
            } else {
                articleList.add(createArticleItem(result))
            }
        }
    }

    private fun createHeaderItem(text: String): HeaderItem = HeaderItem(text)

    private fun createArticleItem(result: Result): ArticleItem = ArticleItem(result.apiUrl, result.fields?.headline, result.fields?.thumbnail, result.webPublicationDate)

    private fun createWeekItem(articleItemList: List<ArticleItem>): WeekItem {
        val weekItem = WeekItem()
        weekItem.articleList.addAll(articleItemList)
        return weekItem
    }
}
