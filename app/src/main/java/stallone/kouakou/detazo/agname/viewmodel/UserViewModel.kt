package stallone.kouakou.detazo.agname.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import stallone.kouakou.detazo.agname.data.db.AppDatabase
import stallone.kouakou.detazo.agname.data.entites.User
import stallone.kouakou.detazo.agname.repository.UserRepository

import java.security.MessageDigest

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun registerUser(username: String, passwordHash: String, isAdmin: Boolean = false, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val existingUser = repository.findByUsername(username)
            if (existingUser == null) {
                repository.insert(User(username = username, passwordHash = passwordHash, isAdmin = isAdmin))
                withContext(Dispatchers.Main) {
                    onResult(true)
                }
            } else {
                withContext(Dispatchers.Main) {
                    onResult(false)
                }
            }
        }
    }

    suspend fun findUserByUsername(username: String): User? = repository.findByUsername(username)

    fun createDefaultAdminIfNotExists() {
        viewModelScope.launch {
            val adminExists = repository.findByUsername("admin")
            if (adminExists == null) {
                val defaultAdminPassword = hashPassword("admin123") // Mot de passe par défaut à changer
                repository.insert(User(username = "admin", passwordHash = defaultAdminPassword, isAdmin = true))
            }
        }
    }
}

fun hashPassword(password: String): String {
    val md = MessageDigest.getInstance("SHA-256")
    val bytes = md.digest(password.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}
