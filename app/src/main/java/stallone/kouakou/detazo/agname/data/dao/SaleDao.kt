package stallone.kouakou.detazo.agname.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import stallone.kouakou.detazo.agname.data.entites.Sale

@Dao
interface SaleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sale: Sale)

    @Query("SELECT * FROM sales ORDER BY date DESC")
    fun getAllSales(): Flow<List<Sale>>

    @Query("SELECT * FROM sales WHERE articleId = :articleId ORDER BY date DESC")
    fun getSalesByArticle(articleId: Int): Flow<List<Sale>>
}