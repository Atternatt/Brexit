package com.sanogueralorenzo.brexit.domain.usecases

import com.sanogueralorenzo.brexit.data.repositories.FavoriteArticleIdListRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddDeleteFavoriteArticleUseCase
@Inject constructor
(private val favoriteArticleIdListRepository: FavoriteArticleIdListRepository)
    : UseCase<Boolean> {

    var articleId = ""

    override fun execute(): Observable<Boolean> = addDelete()

    private fun addDelete(): Observable<Boolean> {
        favoriteArticleIdListRepository.getData().contains(articleId).subscribe()
        return favoriteArticleIdListRepository.getData()
                .map {
                    if (it.contains(articleId)) {
                        favoriteArticleIdListRepository.setData(it.filterNot { it.contains(articleId) })
                        false
                    } else {
                        favoriteArticleIdListRepository.setData(it.plus(articleId))
                        true
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
