package stallone.kouakou.detazo.agname.repository

import kotlinx.coroutines.flow.Flow
import stallone.kouakou.detazo.agname.data.dao.ArticleDao
import stallone.kouakou.detazo.agname.data.entites.Article

class ArticleRepository(private val dao: ArticleDao) {

    val allArticles: Flow<List<Article>> = dao.getAll()

    suspend fun insert(article: Article) {
        dao.insert(article)
    }

    suspend fun update(article: Article) {
        dao.update(article)
    }

    suspend fun delete(article: Article) {
        dao.delete(article)
    }
}