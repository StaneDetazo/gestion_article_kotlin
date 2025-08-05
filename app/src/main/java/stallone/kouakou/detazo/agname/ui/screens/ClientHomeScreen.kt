package stallone.kouakou.detazo.agname.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import stallone.kouakou.detazo.agname.data.session.UserSessionManager
import stallone.kouakou.detazo.agname.viewmodel.UserViewModel

@Composable
fun ClientHomeScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    userSessionManager: UserSessionManager
) {
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Bienvenue Client", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            scope.launch {
                userSessionManager.clearSession()
                navController.navigate("login") {
                    popUpTo("client_home") { inclusive = true }
                }
            }
        }) {
            Text("Déconnexion")
        }

        // Ajoute navigation liste articles etc.
    }
}
