package me.abir.daggerdigging

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.showSnackbarShort(text: String) {
    Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT)
}