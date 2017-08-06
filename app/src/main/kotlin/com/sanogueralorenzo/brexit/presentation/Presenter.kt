package com.sanogueralorenzo.brexit.presentation

import io.reactivex.disposables.CompositeDisposable

interface IView

interface IPresenter<in V : IView> {
    fun attachView(view: V)
    fun detachView()
}

open class Presenter<V : IView> : IPresenter<V> {

    protected var view: V? = null
    protected val disposable : CompositeDisposable = CompositeDisposable()

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
        clear()
    }

    private fun clear(){
        disposable.clear()
    }
}