package com.example.blockbuster.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

inline fun <reified T> ViewGroup.inflate(@LayoutRes layoutRes: Int): T {
    return LayoutInflater.from(context).inflate(layoutRes, this, false) as T
}