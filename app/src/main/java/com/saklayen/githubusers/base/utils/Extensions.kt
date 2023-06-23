package com.saklayen.githubusers.base.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Parcel
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.annotation.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.ParcelCompat
import androidx.core.text.HtmlCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.*
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*


class EmptyClass

fun Fragment.navigate(direction: NavDirections, navOptions: NavOptions? = null) =
    this.findNavController().navigate(direction, navOptions)

fun Fragment.navigateUp() = this.findNavController().navigateUp()
fun Activity.navigate(@IdRes viewId: Int, direction: NavDirections) =
    this.findNavController(viewId).navigate(direction)

/** Convenience for callbacks/listeners whose return value indicates an event was consumed. */
inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}

/**
 * Allows calls like
 *
 * `viewGroup.inflate(R.layout.foo)`
 */
fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}

/**
 * Allows calls like
 *
 * `supportFragmentManager.inTransaction { add(...) }`
 */
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}


/** Write a boolean to a Parcel. */
fun Parcel.writeBooleanUsingCompat(value: Boolean) = ParcelCompat.writeBoolean(this, value)

/** Read a boolean from a Parcel. */
fun Parcel.readBooleanUsingCompat() = ParcelCompat.readBoolean(this)

/**
 * Helper to force a when statement to assert all options are matched in a when statement.
 *
 * By default, Kotlin doesn't care if all branches are handled in a when statement. However, if you
 * use the when statement as an expression (with a value) it will force all cases to be handled.
 *
 * This helper is to make a lightweight way to say you meant to match all of them.
 *
 * Usage:
 *
 * ```
 * when(sealedObject) {
 *     is OneType -> //
 *     is AnotherType -> //
 * }.checkAllMatched
 */
val <T> T.checkAllMatched: T
    get() = this


data class ViewPaddingState(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int,
    val start: Int,
    val end: Int
)

private fun createStateForView(view: View) =
    ViewPaddingState(
        view.paddingLeft,
        view.paddingTop,
        view.paddingRight,
        view.paddingBottom,
        view.paddingLeft,
        view.paddingRight
    )

fun AppCompatActivity.showDialog(
    cancelable: Boolean = true, cancelableTouchOutside: Boolean = true,
    builderFunction: AlertDialog.Builder.() -> Any
) {
    val builder = AlertDialog.Builder(this)
    builder.builderFunction()
    val dialog = builder.create()
    dialog.setCancelable(cancelable)
    dialog.setCanceledOnTouchOutside(cancelableTouchOutside)
    dialog.show()
}

fun Fragment.showDialog(
    cancelable: Boolean = true, cancelableTouchOutside: Boolean = true,
    builderFunction: AlertDialog.Builder.() -> Any
) {
    this.requireContext().showDialog(cancelable, cancelableTouchOutside, builderFunction)
}

fun Context.sharedPreferences(name: String): SharedPreferences =
    this.applicationContext.getSharedPreferences(name, Context.MODE_PRIVATE)


fun Context.showDialog(
    cancelable: Boolean = true, cancelableTouchOutside: Boolean = true,
    builderFunction: AlertDialog.Builder.() -> Any
) {
    val builder = AlertDialog.Builder(this)
    builder.builderFunction()
    val dialog = builder.create()
    dialog.setCancelable(cancelable)
    dialog.setCanceledOnTouchOutside(cancelableTouchOutside)
    dialog.show()
}

fun AlertDialog.Builder.positiveButton(
    text: String,
    handleClick: (i: Int) -> Unit = {}
) {
    this.setPositiveButton(text, { dialogInterface, i -> handleClick(i) })
}

fun AlertDialog.Builder.negativeButton(
    text: String,
    handleClick: (i: Int) -> Unit = {}
) {
    this.setNegativeButton(text, { dialogInterface, i -> handleClick(i) })
}

fun AlertDialog.Builder.neutralButton(text: String, handleClick: (i: Int) -> Unit = {}) {
    this.setNeutralButton(text, { dialogInterface, i -> handleClick(i) })
}


val Context.inputMethodManager: InputMethodManager
    get() = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

val View.inputMethodManager: InputMethodManager
    get() = this.context.inputMethodManager


val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

val Context.dividerItemDecorationVertical
    get() = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

val View.layoutInflater: LayoutInflater
    get() = this.context.layoutInflater


@SuppressLint("ResourceType")
inline fun View.snack(
    @StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG
) {
    snack(resources.getString(messageRes), length)
}

@SuppressLint("ResourceType")
inline fun View.snack(
    @StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG,
    f: Snackbar.() -> Unit
) {
    snack(resources.getString(messageRes), length, f)
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG) {
    val snack = Snackbar.make(this, message, length)
    snack.show()
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

@SuppressLint("ResourceType")
fun Snackbar.action(@IntegerRes actionRes: Int, color: Int? = null, listener: (View) -> Unit) {
    action(view.resources.getString(actionRes), color, listener)
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}

fun <T> Collection<T>?.isNotNullOrEmpty(): Boolean {
    return !this.isNullOrEmpty()
}

fun Any?.isNull() = this == null
fun Any?.isNotNull() = !isNull()

inline fun <T : Any> ifLet(vararg elements: T?, closure: (List<T>) -> Unit) {
    if (elements.all { it != null }) {
        closure(elements.filterNotNull())
    }
}

inline fun <T : Any> guardLet(vararg elements: T?, closure: () -> Nothing): List<T> {
    return if (elements.all { it != null }) {
        elements.filterNotNull()
    } else {
        closure()
    }
}

fun fromHtml(data: String): CharSequence = HtmlCompat.fromHtml(
    data,
    HtmlCompat.FROM_HTML_MODE_COMPACT
)


inline fun <reified T : Enum<T>> Intent.putEnumExtra(victim: T): Intent =
    putExtra(T::class.qualifiedName, victim.ordinal)

inline fun <reified T : Enum<T>> Intent.getEnumExtra(): T? =
    getIntExtra(T::class.qualifiedName, -1)
        .takeUnless { it == -1 }
        ?.let { T::class.java.enumConstants?.get(it) }


fun DrawerLayout.shouldCloseDrawerFromBackPress(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        return rootWindowInsets?.let {
            val systemGestureInsets = it.systemGestureInsets
            return systemGestureInsets.left == 0 && systemGestureInsets.right == 0
        } ?: false
    }
    return true
}

inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}

fun FragmentActivity.findNavControllerByFragmentContainerView(@IdRes viewId: Int): NavController {
    val navHostFragment = this.supportFragmentManager.findFragmentById(viewId) as NavHostFragment
    return navHostFragment.navController
}
