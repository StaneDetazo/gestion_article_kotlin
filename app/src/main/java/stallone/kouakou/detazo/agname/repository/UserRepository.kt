package stallone.kouakou.detazo.agname.repository

import stallone.kouakou.detazo.agname.data.dao.UserDao
import stallone.kouakou.detazo.agname.data.entites.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insert(user: User): Long = userDao.insert(user)

    suspend fun findByUsername(username: String): User? = userDao.findByUsername(username)

    suspend fun findById(id: Int): User? = userDao.findById(id)
}