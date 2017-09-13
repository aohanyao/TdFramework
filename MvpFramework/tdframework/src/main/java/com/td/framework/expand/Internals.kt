/*
 * Copyright 2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.td.framework.expand

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import java.io.Serializable

object AnkoInternals {
    const val NO_GETTER: String = "Property does not have a getter"

    fun noGetter(): Nothing = throw RuntimeException("Property does not have a getter")

    private class AnkoContextThemeWrapper(base: Context?, val theme: Int) : ContextThemeWrapper(base, theme)

    fun <T : View> addView(manager: ViewManager, view: T) {
        return when (manager) {
            is ViewGroup -> manager.addView(view)
            is AnkoContext<*> -> manager.addView(view, null)
            else -> throw RuntimeException("$manager is the wrong parent")
        }
    }

    fun <T : View> addView(ctx: Context, view: T) {
        ctx.UI { AnkoInternals.addView(this, view) }
    }

    fun <T : View> addView(activity: Activity, view: T) {
        createAnkoContext(activity, { AnkoInternals.addView(this, view) }, true)
    }

    fun wrapContextIfNeeded(ctx: Context, theme: Int): Context {
        return if (theme != 0 && (ctx !is AnkoContextThemeWrapper || ctx.theme != theme)) {
            // If the context isn't a ContextThemeWrapper, or it is but does not have
            // the same theme as we need, wrap it in a new wrapper
            AnkoContextThemeWrapper(ctx, theme)
        } else {
            ctx
        }
    }

    fun applyRecursively(v: View, style: (View) -> Unit) {
        style(v)
        if (v is ViewGroup) {
            val maxIndex = v.childCount - 1
            for (i in 0..maxIndex) {
                v.getChildAt(i)?.let { applyRecursively(it, style) }
            }
        }
    }

    fun getContext(manager: ViewManager): Context = when (manager) {
        is ViewGroup -> manager.context
        is AnkoContext<*> -> manager.ctx
        else -> throw RuntimeException("$manager is the wrong parent")
    }

    inline fun <T> T.createAnkoContext(
            ctx: Context,
            init: AnkoContext<T>.() -> Unit,
            setContentView: Boolean = false
    ): AnkoContext<T> {
        val dsl = AnkoContextImpl(ctx, this, setContentView)
        dsl.init()
        return dsl
    }

    // Some constants not present in Android SDK v.15
    private object InternalConfiguration {
        val SCREENLAYOUT_LAYOUTDIR_MASK = 0xC0
        val SCREENLAYOUT_LAYOUTDIR_SHIFT = 6
        val SCREENLAYOUT_LAYOUTDIR_RTL = 0x02 shl SCREENLAYOUT_LAYOUTDIR_SHIFT

        val UI_MODE_TYPE_APPLIANCE = 0x05
        val UI_MODE_TYPE_WATCH = 0x06
    }

    @JvmStatic
    fun <T> createIntent(ctx: Context, clazz: Class<out T>, params: Array<out Pair<String, Any?>>): Intent {
        val intent = Intent(ctx, clazz)
        if (params.isNotEmpty()) fillIntentArguments(intent, params)
        return intent
    }

    @JvmStatic
    fun internalStartActivity(
            ctx: Context,
            activity: Class<out Activity>,
            params: Array<out Pair<String, Any>>
    ) {
        ctx.startActivity(createIntent(ctx, activity, params))
    }

    @JvmStatic
    fun internalStartActivityForResult(
            act: Activity,
            activity: Class<out Activity>,
            requestCode: Int,
            params: Array<out Pair<String, Any>>
    ) {
        act.startActivityForResult(createIntent(act, activity, params), requestCode)
    }

    @JvmStatic
    fun internalStartService(
            ctx: Context,
            activity: Class<out Service>,
            params: Array<out Pair<String, Any>>
    ) {
        ctx.startService(createIntent(ctx, activity, params))
    }

    @JvmStatic
    private fun fillIntentArguments(intent: Intent, params: Array<out Pair<String, Any?>>) {
        params.forEach {
            val value = it.second
            when (value) {
                null -> intent.putExtra(it.first, null as Serializable?)
                is Int -> intent.putExtra(it.first, value)
                is Long -> intent.putExtra(it.first, value)
                is CharSequence -> intent.putExtra(it.first, value)
                is String -> intent.putExtra(it.first, value)
                is Float -> intent.putExtra(it.first, value)
                is Double -> intent.putExtra(it.first, value)
                is Char -> intent.putExtra(it.first, value)
                is Short -> intent.putExtra(it.first, value)
                is Boolean -> intent.putExtra(it.first, value)
                is Serializable -> intent.putExtra(it.first, value)
                is Bundle -> intent.putExtra(it.first, value)
                is Parcelable -> intent.putExtra(it.first, value)
                is Array<*> -> when {
                    value.isArrayOf<CharSequence>() -> intent.putExtra(it.first, value)
                    value.isArrayOf<String>() -> intent.putExtra(it.first, value)
                    value.isArrayOf<Parcelable>() -> intent.putExtra(it.first, value)
                    else -> throw RuntimeException("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
                }
                is IntArray -> intent.putExtra(it.first, value)
                is LongArray -> intent.putExtra(it.first, value)
                is FloatArray -> intent.putExtra(it.first, value)
                is DoubleArray -> intent.putExtra(it.first, value)
                is CharArray -> intent.putExtra(it.first, value)
                is ShortArray -> intent.putExtra(it.first, value)
                is BooleanArray -> intent.putExtra(it.first, value)
                else -> throw RuntimeException("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            return@forEach
        }
    }

    // Cursor is not closeable in older versions of Android
    @JvmStatic
    inline fun <T> useCursor(cursor: Cursor, f: (Cursor) -> T): T {
        try {
            return f(cursor)
        } finally {
            try {
                cursor.close()
            } catch (e: Exception) {
                // Do nothing
            }
        }
    }

    @JvmStatic
    fun <T : View> initiateView(ctx: Context, viewClass: Class<T>): T {
        fun getConstructor1() = viewClass.getConstructor(Context::class.java)
        fun getConstructor2() = viewClass.getConstructor(Context::class.java, AttributeSet::class.java)

        try {
            return getConstructor1().newInstance(ctx)
        } catch (e: NoSuchMethodException) {
            try {
                return getConstructor2().newInstance(ctx, null)
            } catch (e: NoSuchMethodException) {
                throw RuntimeException("Can't initiate View of class ${viewClass.name}: can't find proper constructor")
            }
        }

    }

}