package stallone.kouakou.detazo.agname.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import stallone.kouakou.detazo.agname.data.db.AppDatabase
import stallone.kouakou.detazo.agname.data.entites.Article
import stallone.kouakou.detazo.agname.repository.ArticleRepository

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ArticleRepository
    val allArticles: LiveData<List<Article>>

    init {
        val dao = AppDatabase.getDatabase(application).articleDao()
        repository = ArticleRepository(dao)
        allArticles = repository.allArticles.asLiveData()
    }

    fun insert(article: Article) = viewModelScope.launch {
        repository.insert(article)
    }

    fun update(article: Article) = viewModelScope.launch {
        repository.update(article)
    }

    fun delete(article: Article) = viewModelScope.launch {
        repository.delete(article)
    }

//    fun getArticleById(articleId: Int): Flow<Article?> {
//        return repository.getArticleById(articleId)
//    }

}