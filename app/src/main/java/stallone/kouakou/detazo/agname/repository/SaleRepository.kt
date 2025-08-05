package stallone.kouakou.detazo.agname.repository

import kotlinx.coroutines.flow.Flow
import stallone.kouakou.detazo.agname.data.dao.ArticleDao
import stallone.kouakou.detazo.agname.data.dao.SaleDao
import stallone.kouakou.detazo.agname.data.entites.Sale

class SaleRepository(
    private val saleDao: SaleDao,
    private val articleDao: ArticleDao
) {
    suspend fun insert(sale: Sale) {
        saleDao.insert(sale)
    }

    suspend fun sellArticle(articleId: Int, quantity: Int) {
        // 1. Mettre à jour le stock
        articleDao.decrementStock(articleId, quantity)

        // 2. Enregistrer la vente
        val sale = Sale(articleId = articleId, quantitySold = quantity)
        saleDao.insert(sale)
    }

    fun getAllSales(): Flow<List<Sale>> = saleDao.getAllSales()

    fun getSalesByArticle(articleId: Int): Flow<List<Sale>> = saleDao.getSalesByArticle(articleId)
}
