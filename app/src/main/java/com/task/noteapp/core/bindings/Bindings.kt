package com.task.noteapp.core.bindings

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.task.noteapp.core.extension.loadImage


@BindingAdapter("bind:image")
fun bindImage(view: AppCompatImageView, url: String?) {
    url?.let {
        if (it.isNotEmpty() && it != null)
            view.loadImage(it)
        else
            view.visibility = View.GONE
    }
}

@BindingAdapter("bind:visibility")
fun visible(view: View, string: String?) {
    string?.let {
        if (it.isNotEmpty())
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }
}

@BindingAdapter("bind:visibilityBoolean")
fun visible(view: View, boolean: Boolean) {
    boolean?.let {
        if (boolean)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }
}

