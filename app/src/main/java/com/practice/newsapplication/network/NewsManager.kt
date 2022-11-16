package com.practice.newsapplication.network

import android.util.Log
import androidx.compose.runtime.*
import com.practice.newsapplication.models.ArticleCategory
import com.practice.newsapplication.models.TopNewsResponse
import com.practice.newsapplication.models.getArticleCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager {

    private val _newsResponse = mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _newsResponse
        }

    private val _getArticleByCategory = mutableStateOf(TopNewsResponse())
    val getArticleCategory: State<TopNewsResponse>
        @Composable get() = remember {
            _getArticleByCategory
        }

    val selectedCategory : MutableState<ArticleCategory?> = mutableStateOf(null)

    init{
        getArticles()
    }

    private fun getArticles(){
        val service = Api.retrofitService.getTopArticles("US", Api.API_KEY)
        service.enqueue(object  : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if(response.isSuccessful){
                    _newsResponse.value = response.body()!!
                    Log.d("news", "${_newsResponse.value}")
                }else{
                    Log.d("error", "${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }
        })
    }

    fun getArticleByCategory(category: String){
        val service = Api.retrofitService.getArticlesByCategory(category, Api.API_KEY)
        service.enqueue(object  : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if(response.isSuccessful){
                    _getArticleByCategory.value = response.body()!!
                    Log.d("category", "${_getArticleByCategory.value}")
                }else{
                    Log.d("error", response.message())
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }
        })
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getArticleCategory(category)
        selectedCategory.value = newCategory
    }
}