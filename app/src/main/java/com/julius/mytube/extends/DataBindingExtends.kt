package com.julius.mytube.extends

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * build.gradle:
 * android{
 *      ...
 *      buildFeatures {
 *          dataBinding = true
 *      }
 * }
 *
 * Created by Developer Zailong Shi on 2020/5/28.
 */
inline fun <reified T : ViewDataBinding> ViewGroup.inflate(
    inflater: LayoutInflater? = null, attach: Boolean = true
): T =
    T::class.java.getDeclaredMethod(
        "inflate",
        LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
    ).invoke(
        null, inflater ?: LayoutInflater.from(context), this, attach
    ) as T

inline fun <reified T : ViewDataBinding> Fragment.inflate(
    inflater: LayoutInflater? = null, container: ViewGroup?
): T =
    (T::class.java.getDeclaredMethod(
        "inflate",
        LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
    ).invoke(
        null, inflater ?: LayoutInflater.from(context), container, false
    ) as T).let {
        it.lifecycleOwner = this
        it
    }

inline fun <reified T : ViewDataBinding> Activity.setContentView(): T =
    (T::class.java.getDeclaredMethod(
        "inflate",
        LayoutInflater::class.java
    ).invoke(
        null, LayoutInflater.from(this)
    ) as T).let {
        setContentView(it.root)
        if (this is ComponentActivity) {
            it.lifecycleOwner = this
        }
        it
    }