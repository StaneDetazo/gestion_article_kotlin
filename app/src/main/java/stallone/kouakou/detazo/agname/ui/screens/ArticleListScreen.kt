package stallone.kouakou.detazo.agname.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import stallone.kouakou.detazo.agname.data.entites.Article
import stallone.kouakou.detazo.agname.viewmodel.ArticleViewModel
import stallone.kouakou.detazo.agname.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen(
    articleViewModel: ArticleViewModel,
    cartViewModel: CartViewModel,
    onAddClick: () -> Unit,
    onArticleClick: (Int) -> Unit,
    isAdmin: Boolean
) {
    val articles by articleViewModel.allArticles.observeAsState(emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Articles en stock") }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (isAdmin) {
                FloatingActionButton(onClick = onAddClick) {
                    Icon(Icons.Default.Add, contentDescription = "Ajouter")
                }
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (articles.isEmpty()) {
                item {
                    Text("Aucun article disponible.")
                }
            } else {
                items(articles) { article ->
                    ArticleItem(
                        article = article,
                        onClick = { onArticleClick(article.id) },
                        onAddToCart = {
                            cartViewModel.addToCart(article)
                            scope.launch {
                                snackbarHostState.showSnackbar("« ${article.name} » ajouté au panier !")
                            }
                        },
                        isAdmin = isAdmin
                    )
                }
            }
        }
    }
}

@Composable
fun ArticleItem(
    article: Article,
    onClick: () -> Unit,
    onAddToCart: () -> Unit,
    isAdmin: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .then(
                if (isAdmin) Modifier.clickable { onClick() }
                else Modifier
            ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(article.name, style = MaterialTheme.typography.titleMedium)
            Text("Catégorie : ${article.category}")
            Text("Prix : ${article.price} FCFA")
            Text("Quantité : ${article.quantity}")
            if (!article.isActive) {
                Text("Inactif", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(8.dp))
            if (!isAdmin) {

                Button(
                    onClick = onAddToCart,
                    enabled = article.isActive && article.quantity > 0
                ) {
                    Text("Ajouter au panier")
                }
            }
        }
    }
}
