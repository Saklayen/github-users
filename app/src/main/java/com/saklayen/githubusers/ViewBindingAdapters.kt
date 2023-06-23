package com.saklayen.githubusers

import android.os.Build
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputLayout
import com.saklayen.githubusers.base.utils.CircularOutlineProvider


@BindingAdapter("srcCompatById")
fun AppCompatImageView.srcCompatById(@DrawableRes id: Int) {
    this.setImageResource(id)
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@BindingAdapter("clipToCircle")
fun clipToCircle(view: View, clip: Boolean) {
    view.clipToOutline = clip
    view.outlineProvider = if (clip) CircularOutlineProvider else null
}

@BindingAdapter("error")
fun error(view: TextInputLayout, errorMessages: String) {
    view.error = errorMessages
}

@BindingAdapter("goneUnless")
fun View.goneUnless(visible: Boolean) {
    this.visibility = if (visible) GONE else VISIBLE
}

@BindingAdapter("goneLayoutUnless")
fun LinearLayout.goneLayoutUnless(visible: Boolean) {
    this.visibility = if (visible) VISIBLE else GONE
}

@BindingAdapter("contentTextOrGone")
fun TextView.contentTextOrGone(data: String?) {
    if (data != null && data.isNotEmpty()) {
        this.visibility = VISIBLE
    } else this.visibility = GONE
}

@BindingAdapter("textGoneUnless")
fun TextView.textGoneUnless(data: String?) {
    if (data != null && data.isNotEmpty()) {
        this.visibility = VISIBLE
        text = data
    } else this.visibility = GONE
}

@BindingAdapter("htmlTextGoneUnless")
fun TextView.htmlTextGoneUnless(data: String?) {
    if (data != null && data.isNotEmpty()) {
        this.visibility = VISIBLE
        text = HtmlCompat.fromHtml(data, HtmlCompat.FROM_HTML_MODE_COMPACT)
        movementMethod = LinkMovementMethod.getInstance()
    } else this.visibility = GONE

}

@BindingAdapter("invisibleUnless")
fun invisibleUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) VISIBLE else INVISIBLE
}

@BindingAdapter("disableUnless")
fun disableUnless(view: View, disable: Boolean) {
    view.isEnabled = !disable
}

@BindingAdapter(value = ["bind_view_page_tabs", "entries"], requireAll = true)
fun bindViewPagerTabs(view: TabLayout, viewPager: ViewPager2, date: Array<String>) {
    TabLayoutMediator(view, viewPager) { tab, position ->
        tab.text = date[position]
    }.attach()
}

@BindingAdapter("html_text")
fun TextView.bindHtmlText(data: String?) {
    data?.let {
        text = HtmlCompat.fromHtml(data, HtmlCompat.FROM_HTML_MODE_COMPACT)
        movementMethod = LinkMovementMethod.getInstance()
    }
}

@BindingAdapter("adapter")
fun RecyclerView.bindAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
    this.adapter = adapter
}

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        //.placeholder(R.drawable.avatar)
        .into(view)
}

@BindingAdapter("setOnQueryTextListener")
fun SearchView.setOnQueryTextListener(listener: SearchView.OnQueryTextListener) {
    this.setOnQueryTextListener(listener)
}
