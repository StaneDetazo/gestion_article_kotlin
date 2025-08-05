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
fun RegisterScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Créer un compte", style = MaterialTheme.typography.headlineMedium)

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

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = passwordConfirm,
            onValueChange = { passwordConfirm = it },
            label = { Text("Confirmer mot de passe") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                message = null
                if (username.isBlank() || password.isBlank()) {
                    message = "Veuillez remplir tous les champs"
                    return@Button
                }
                if (password != passwordConfirm) {
                    message = "Les mots de passe ne correspondent pas"
                    return@Button
                }

                scope.launch {
                    val hashedPwd = hashPassword(password)
                    userViewModel.registerUser(username, hashedPwd) { success ->
                        if (success) {
                            message = "Compte créé avec succès. Connectez-vous."
                            // Optionnel : revenir à l’écran login automatiquement
                            navController.popBackStack()
                        } else {
                            message = "Nom d’utilisateur déjà pris"
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("S’inscrire")
        }

        message?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Retour à la connexion")
        }
    }
}
