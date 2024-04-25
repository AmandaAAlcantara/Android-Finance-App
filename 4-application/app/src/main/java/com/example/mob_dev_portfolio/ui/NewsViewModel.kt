package com.example.mob_dev_portfolio.ui
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mob_dev_portfolio.models.NewsResponse
import com.example.mob_dev_portfolio.repository.NewsRepository
import com.example.mob_dev_portfolio.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    init {
        getBreakingNews("gb", "business")
    }

    //only alive if the view is alive
    fun getBreakingNews(countryCode: String, category: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        //suspend function
        val response = newsRepository.getBreakingNews(countryCode, category,  breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

     fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}




