package com.example.nayabazar.utils

import android.view.View

class Constants {
    companion object {
        fun showProgressBar(view: View) {
            view.visibility = View.VISIBLE
        }
        fun hideProgressBar(view: View) {
            view.visibility = View.GONE
        }


    }
}