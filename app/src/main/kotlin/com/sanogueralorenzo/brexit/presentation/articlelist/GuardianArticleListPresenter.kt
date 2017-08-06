package com.sanogueralorenzo.brexit.presentation.articlelist

import com.sanogueralorenzo.brexit.data.model.Result
import com.sanogueralorenzo.brexit.domain.repositories.ArticleListRepository
import com.sanogueralorenzo.brexit.presentation.*
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.header.HeaderItem
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.WeekItem
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.article.ArticleItem
import com.sanogueralorenzo.brexit.presentation.commons.ViewType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

interface GuardianArticleListView : IView {
    fun addItemList(itemList: List<ViewType>)
    fun onError()
}

class GuardianArticleListPresenter(val articleListRepository: ArticleListRepository) : Presenter<GuardianArticleListView>() {

    override fun attachView(view: GuardianArticleListView) {
        super.attachView(view)
        getCacheArticleList()
        getArticleList()
    }

    fun getCacheArticleList() {
        val articleList = articleListRepository.getCacheArticleList()
        if (!articleList.isEmpty()) {
            view?.addItemList(createItemList(ArrayList(articleList)))
        }
    }

    fun getArticleList() {
        disposable.add(articleListRepository.getArticleList(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.response.results.orderByDescendingWebPublicationDate() }
                .doOnNext { save(it) }
                .subscribe({ view?.addItemList(createItemList(ArrayList(it))) }, { view?.onError() }))
    }

    fun createItemList(resultList: ArrayList<Result>): List<ViewType> {
        val itemList = ArrayList<ViewType>()
        addFavorites(resultList, itemList)
        addOthers(resultList, itemList)
        return itemList
    }

    fun addFavorites(resultList: ArrayList<Result>, itemList: ArrayList<ViewType>) {
        val favoriteArticleUrlList = ArrayList<String>()
        favoriteArticleUrlList.addAll(articleListRepository.getFavoriteArticleList() ?: ArrayList<String>())

        if (favoriteArticleUrlList.isNotEmpty()) {
            val favoriteArticleList = ArrayList<ArticleItem>()
            //Move favorites from result list to favoriteArticleList
            resultList
                    .filter { favoriteArticleUrlList.contains(it.apiUrl) }
                    .forEach {
                        favoriteArticleList.add(createArticleItem(it))
                        resultList.remove(it)
                    }
            if (!favoriteArticleList.isEmpty()){
                itemList.add(createHeaderItem("Favorites"))
                itemList.add(createWeekItem(favoriteArticleList))
            }
        }
    }

    fun addOthers(resultList: ArrayList<Result>, itemList: ArrayList<ViewType>) {
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

    fun createArticleItem(result: Result): ArticleItem {
        return ArticleItem(result.apiUrl, result.fields?.headline, result.fields?.thumbnail, result.webPublicationDate)
    }

    fun createWeekItem(articleItemList: List<ArticleItem>): WeekItem {
        val weekItem = WeekItem()
        weekItem.articleList.addAll(articleItemList)
        return weekItem
    }

    fun createHeaderItem(text: String): HeaderItem {
        return HeaderItem(text)
    }

    fun save(articleList: List<Result>) {
        articleListRepository.saveArticleList(articleList)
    }
}