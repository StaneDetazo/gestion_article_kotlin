package stallone.kouakou.detazo.agname.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import stallone.kouakou.detazo.agname.data.session.UserSessionManager
import stallone.kouakou.detazo.agname.viewmodel.UserViewModel

@Composable
fun AdminHomeScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    userSessionManager: UserSessionManager
) {
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Bienvenue Admin", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("article_list")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gérer les articles")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    userSessionManager.clearSession()
                    navController.navigate("login") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Déconnexion", color = Color.White)
        }
    }
}
//
//@Composable
//fun AdminHomeScreen(
//    navController: NavController,
//    userViewModel: UserViewModel,
//    userSessionManager: UserSessionManager
//) {
//    val scope = rememberCoroutineScope()
//
//    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        Text("Bienvenue Admin", style = MaterialTheme.typography.headlineMedium)
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(onClick = {
//            scope.launch {
//                userSessionManager.clearSession()
//                navController.navigate("login") {
//                    popUpTo("admin_home") { inclusive = true }
//                }
//            }
//        }) {
//            Text("Déconnexion")
//        }
//
//        // Ajoute navigation gestion articles etc.
//    }
//}
