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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
                title = { Text("Articles en stock", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D1B2A), // Bleu très foncé
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (isAdmin) {
                FloatingActionButton(
                    onClick = onAddClick,
                    containerColor = Color(0xFF1B263B),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Ajouter")
                }
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            if (articles.isEmpty()) {
                item {
                    Text("Aucun article disponible.", color = Color.Gray)
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
    val bgColor = if (article.isActive) Color(0xFF415A77) else Color(0xFF6C757D) // gris-bleu
    val textColor = Color.White
    val buttonColor = Color(0xFF1B263B)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(enabled = isAdmin) { onClick() },
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(article.name, color = textColor, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(4.dp))
                Text("Catégorie : ${article.category}", color = Color.LightGray)
                Text("Prix : ${article.price} FCFA", color = Color.LightGray)
                Text("Quantité : ${article.quantity}", color = Color.LightGray)

                if (!article.isActive) {
                    Text("Inactif", color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)
                }
            }

            if (!isAdmin && article.isActive && article.quantity > 0) {
                Button(
                    onClick = onAddToCart,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.align(Alignment.Bottom)
                ) {
                    Text("Ajouter")
                }
            }
        }
    }
}
