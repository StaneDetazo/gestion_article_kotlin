package stallone.kouakou.detazo.agname.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import stallone.kouakou.detazo.agname.data.entites.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun findByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): User?
}