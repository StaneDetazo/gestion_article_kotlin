package stallone.kouakou.detazo.agname.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import stallone.kouakou.detazo.agname.data.dao.ArticleDao
import stallone.kouakou.detazo.agname.data.dao.SaleDao
import stallone.kouakou.detazo.agname.data.dao.UserDao
import stallone.kouakou.detazo.agname.data.entites.Article
import stallone.kouakou.detazo.agname.data.entites.Sale
import stallone.kouakou.detazo.agname.data.entites.User

@Database(entities = [Article::class, Sale::class, User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun saleDao(): SaleDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "article_database6"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
