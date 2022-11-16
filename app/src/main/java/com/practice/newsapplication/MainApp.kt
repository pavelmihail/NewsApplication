package com.practice.newsapplication

import android.app.Application
import com.practice.newsapplication.data.Repository
import com.practice.newsapplication.network.Api
import com.practice.newsapplication.network.NewsManager

class MainApp: Application() {
    private val manager by lazy {
        NewsManager(Api.retrofitService)
    }

    val repository by lazy{
        Repository(manager = manager)
    }
}