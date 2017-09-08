package com.sanogueralorenzo.brexit.presentation

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sanogueralorenzo.brexit.data.model.Result
import java.text.SimpleDateFormat
import java.util.*

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun View.gone() {
    this.visibility = View.GONE
}

fun ImageView.loadUrl(url: String?) {
    Glide.with(context).load(url).into(this)
}

fun ImageView.loadUrlRound(url: String?) {
    Glide.with(context).load(url).apply(RequestOptions.circleCropTransform()).into(this)
}

fun AppBarLayout.removeScrolling() {
    val params = this.layoutParams as CoordinatorLayout.LayoutParams
    params.behavior = AppBarLayout.Behavior()
    val behavior = params.behavior as AppBarLayout.Behavior
    behavior.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
        override fun canDrag(appBarLayout: AppBarLayout): Boolean {
            return false
        }
    })
}

fun AppBarLayout.addCollapsingToolbarCollapsedTitle(collapsingToolbarLayout: CollapsingToolbarLayout, toolbarTitle: String) {
    this.addOnOffsetChangedListener({ appBarLayout1, verticalOffset ->
        val percentage = Math.abs(verticalOffset).toFloat() / appBarLayout1.totalScrollRange
        if (percentage.toInt() != 0) {
            collapsingToolbarLayout.title = toolbarTitle
        } else {
            collapsingToolbarLayout.title = " "
        }
    })
}

fun AppBarLayout.addFadingToolbarCollapsedTitle(collapsingToolbarLayout: CollapsingToolbarLayout) {
    this.addOnOffsetChangedListener({ appBarLayout1, verticalOffset ->
        val percentage = Math.abs(verticalOffset).toFloat() / appBarLayout1.totalScrollRange
        collapsingToolbarLayout.alpha = 1 - percentage
    })
}


fun List<Result>.orderByDescendingWebPublicationDate(): List<Result> {
    Collections.sort(this, { o1: Result, o2: Result ->
        -(o1.webPublicationDate?.compareTo(o2.webPublicationDate))!!
    })
    return this
}

fun Date.dayMonthYearFormat(): String? {
    return SimpleDateFormat("dd/MM/yyyy").format(this)
}

fun Date.weekAgoFormat(): String? {
    val todayDate = Date()
    val millisWeek = 604800000
    val millisDifference = todayDate.time - this.time
    val weeksAgo = millisDifference / millisWeek
    return when (weeksAgo.toInt()) {
        0 -> "This week"
        1 -> "Last week"
        else -> weeksAgo.toString() + " Weeks ago"
    }
}

fun Date.isNewWeek(other: Date): Boolean {
    val todayDate = Date()
    val millisWeek = 604800000

    val millisDifference = todayDate.time - this.time
    val weeksAgo = millisDifference / millisWeek

    val millisOtherDifference = todayDate.time - other.time
    val weeksOtherAgo = millisOtherDifference / millisWeek

    return weeksAgo.toInt() != weeksOtherAgo.toInt()
}