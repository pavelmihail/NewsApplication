package com.practice.newsapplication.ui

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.practice.newsapplication.MainApp
import com.practice.newsapplication.models.ArticleCategory
import com.practice.newsapplication.models.TopNewsResponse
import com.practice.newsapplication.models.getArticleCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = getApplication<MainApp>().repository

    private val _newsResponse = MutableStateFlow(TopNewsResponse())
    val newsResponse: StateFlow<TopNewsResponse>
    get() = _newsResponse

    private val _isLoading = MutableStateFlow(true)
    val isLoading : StateFlow<Boolean> = _isLoading

    fun getTopArticles(){
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            _newsResponse.value = repository.getArticles()
        }
        _isLoading.value = false
    }

    private val _getArticleByCategory = MutableStateFlow(TopNewsResponse())
    val getArticleByCategory: StateFlow<TopNewsResponse>
        get() = _getArticleByCategory

    val sourceName = MutableStateFlow("engadget")
    private val _getArticleBySource = MutableStateFlow(TopNewsResponse())
    val getArticleBySource: StateFlow<TopNewsResponse>
    get() = _getArticleBySource

    private val _selectedCategory : MutableStateFlow<ArticleCategory?> = MutableStateFlow(null)
    val selectedCategory: StateFlow<ArticleCategory?>
        get() = _selectedCategory


    fun getArticlesByCategory(category: String){
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            _getArticleByCategory.value = repository.getArticlesByCategory(category)
        }

        _isLoading.value = false
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getArticleCategory(category)
        _selectedCategory.value = newCategory
    }

    fun getArticleBySource(){
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _getArticleBySource.value = repository.getArticlesBySource(sourceName.value)
        }
        _isLoading.value = false
    }

}