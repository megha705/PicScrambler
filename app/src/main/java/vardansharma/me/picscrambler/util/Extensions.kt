package com.example.vardansharma.simplesttodoappever.utils

/**
 * Created by vardansharma on 14/05/17.
 */


import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

/**
 * Created by vardansharma on 14/05/17.
 */

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.toast(message: Int) =
        Toast.makeText(this, this.getText(message), Toast.LENGTH_SHORT).show()

fun CharSequence.isValidEmail(): Boolean {
    when (this) {
        null -> return false
        else -> return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}


fun View.gone() {
    this.visibility = GONE
}

fun View.show() {
    this.visibility = VISIBLE

}

fun View.invisible() {
    this.visibility = INVISIBLE
}

fun ImageView.loadImage(url: String) {
    Picasso.with(this.context)
            .load(url)
            .into(this)

}


fun ImageView.loadImage(url: String, callback: Callback) {
    Picasso.with(this.context)
            .load(url)
            .into(this, callback)

}

fun Context.dpToPixel(dp: Int): Int {
    val resources = this.resources
    val metrics = resources.displayMetrics
    val px = dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    return px.toInt()
}