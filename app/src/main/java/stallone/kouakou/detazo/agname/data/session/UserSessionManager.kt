package stallone.kouakou.detazo.agname.data.session

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property pour DataStore
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserSessionManager(private val context: Context) {

    companion object {
        private val KEY_USERNAME = stringPreferencesKey("username")
        private val KEY_IS_ADMIN = booleanPreferencesKey("is_admin")
    }

    // Flow pour le nom d'utilisateur (nullable)
    val userNameFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_USERNAME]
    }

    // Flow pour le statut admin, false par défaut
    val isAdminFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_IS_ADMIN] ?: false
    }

    // Sauvegarde de la session utilisateur
    suspend fun saveUserSession(username: String, isAdmin: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[KEY_USERNAME] = username
            prefs[KEY_IS_ADMIN] = isAdmin
        }
    }

    // Effacement de la session
    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
