package com.sanogueralorenzo.brexit.presentation

interface IView

interface IPresenter<in V : IView> {
    fun attachView(view: V)
    fun detachView()
}

open class Presenter<V : IView> : IPresenter<V> {

    protected var view: V? = null

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}
