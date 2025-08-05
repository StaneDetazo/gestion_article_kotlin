package stallone.kouakou.detazo.agname.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import stallone.kouakou.detazo.agname.viewmodel.UserViewModel
import stallone.kouakou.detazo.agname.viewmodel.hashPassword

@Composable
fun LoginScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(),
    userSessionManager: stallone.kouakou.detazo.agname.data.session.UserSessionManager
) {
    val scope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Connexion", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nom d’utilisateur") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    val user = userViewModel.findUserByUsername(username)
                    if (user != null) {
                        val hashedInput = hashPassword(password)
                        if (user.passwordHash == hashedInput) {
                            // Sauvegarder session
                            userSessionManager.saveUserSession(user.username, user.isAdmin)

                            message = "Connexion réussie"

                            // Naviguer selon rôle
                            if (user.isAdmin) {
                                navController.navigate("admin_home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                navController.navigate("client_home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        } else {
                            message = "Mot de passe incorrect"
                        }
                    } else {
                        message = "Utilisateur non trouvé"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Se connecter")
        }

        message?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = { navController.navigate("register") }) {
            Text("Créer un compte")
        }
    }
}