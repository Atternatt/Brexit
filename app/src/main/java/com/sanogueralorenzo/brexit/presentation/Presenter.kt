package com.sanogueralorenzo.brexit.presentation

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

interface IView

interface IPresenter<in V : IView> {
    fun attachView(view: V)
    fun detachView()
}

abstract class Presenter<V : IView> : IPresenter<V> {

    abstract val view: WeakReference<V>

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun detachView() {
        clearDisposable()
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun clearDisposable() {
        compositeDisposable.clear()
    }
}
