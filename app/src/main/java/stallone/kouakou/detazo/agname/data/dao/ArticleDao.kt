package stallone.kouakou.detazo.agname.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import stallone.kouakou.detazo.agname.data.entites.Article

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles ORDER BY id DESC")
    fun getAll(): Flow<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Update
    suspend fun update(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Query("UPDATE articles SET quantity = quantity - :qtySold WHERE id = :articleId AND quantity >= :qtySold")
    suspend fun decrementStock(articleId: Int, qtySold: Int)

}