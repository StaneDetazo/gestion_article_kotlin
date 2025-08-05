package stallone.kouakou.detazo.agname.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import stallone.kouakou.detazo.agname.viewmodel.ArticleViewModel
import stallone.kouakou.detazo.agname.viewmodel.SaleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaleScreen(
    navController: NavController,
    articleViewModel: ArticleViewModel,
    saleViewModel: SaleViewModel = viewModel()
) {
    // Utilisation de LiveData au lieu de collectAsStateWithLifecycle
    val articles by articleViewModel.allArticles.observeAsState(emptyList())

    var selectedArticleIndex by remember { mutableStateOf(-1) }
    var quantityInput by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Nouvelle Vente", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(onClick = { expanded = true }) {
            Text(
                if (selectedArticleIndex != -1) articles[selectedArticleIndex].name
                else "Sélectionner un article"
            )
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            articles.forEachIndexed { index, article ->
                DropdownMenuItem(
                    text = { Text(article.name) },
                    onClick = {
                        selectedArticleIndex = index
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = quantityInput,
            onValueChange = { quantityInput = it },
            label = { Text("Quantité vendue") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (selectedArticleIndex != -1) {
            val stock = articles[selectedArticleIndex].quantity
            Text("Stock disponible : $stock", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val qty = quantityInput.toIntOrNull()
                val article = articles.getOrNull(selectedArticleIndex)

                message = when {
                    article == null || qty == null || qty <= 0 ->
                        "Veuillez sélectionner un article et entrer une quantité valide."

                    qty > article.quantity ->
                        "Stock insuffisant. Stock disponible : ${article.quantity}"

                    else -> {
                        saleViewModel.sellArticle(article.id, qty)
                        quantityInput = ""
                        "Vente enregistrée avec succès."
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Valider la vente")
        }

        message?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it,
                color = if (it.startsWith("Vente")) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.error
            )
        }
    }
}

