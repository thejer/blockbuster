package com.example.blockbuster.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.google.android.material.snackbar.Snackbar

inline fun <reified T> ViewGroup.inflate(@LayoutRes layoutRes: Int): T {
    return LayoutInflater.from(context).inflate(layoutRes, this, false) as T
}

fun View.showSnackbar(
    snackbarText: String,
    timeLength: Int,
    actionString: String?,
    action: () -> Unit?
): Snackbar {
    val snackbar = Snackbar.make(this, snackbarText, timeLength)
    snackbar.run {
        setAction(actionString) {
            snackbar.dismiss()
            action()
        }
        show()
    }
    return snackbar
}
