package com.sanogueralorenzo.brexit.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun View.gone() {
    this.visibility = View.GONE
}

fun ImageView.loadurl(url: String?) {
    Glide.with(this.context).load(url).into(this)
}

fun ImageView.loadUrlRound(url: String?) {
    Glide.with(context).load(url).apply(RequestOptions.circleCropTransform()).into(this)
}

fun Date.dayMonthYearFormat(): String? {
    return SimpleDateFormat("dd/MM/yyyy").format(this)
}

fun Date.weekAgoFormat(): String? {
    val todayDate = Date()
    val millisWeek = 604800000
    val millisDifference = todayDate.time - this.time
    val weeksAgo = millisDifference / millisWeek
    when (weeksAgo.toInt()) {
        0 -> return "This week"
        1 -> return "Last week"
        else -> return weeksAgo.toString() + " Weeks ago"
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

