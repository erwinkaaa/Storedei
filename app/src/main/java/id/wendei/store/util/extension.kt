package id.wendei.store.util

import android.view.View
import android.widget.ImageView

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun ImageView.load(url: String) {
    GlideApp.with(context).load(url).into(this)
}