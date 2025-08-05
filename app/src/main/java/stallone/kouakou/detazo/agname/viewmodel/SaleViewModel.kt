package stallone.kouakou.detazo.agname.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import stallone.kouakou.detazo.agname.data.db.AppDatabase
import stallone.kouakou.detazo.agname.data.entites.Article
import stallone.kouakou.detazo.agname.data.entites.Sale
import stallone.kouakou.detazo.agname.repository.SaleRepository

class SaleViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SaleRepository

    init {
        val db = AppDatabase.getDatabase(application)
        val saleDao = db.saleDao()
        val articleDao = db.articleDao()
        repository = SaleRepository(saleDao, articleDao)
    }

    fun sellArticle(articleId: Int, quantity: Int) = viewModelScope.launch {
        repository.sellArticle(articleId, quantity)
    }

    fun insert(sale: Sale) = viewModelScope.launch {
        repository.insert(sale)
    }

    fun getAllSales(): Flow<List<Sale>> = repository.getAllSales()

    fun getSalesByArticle(articleId: Int): Flow<List<Sale>> =
        repository.getSalesByArticle(articleId)



}