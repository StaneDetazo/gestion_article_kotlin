package stallone.kouakou.detazo.agname.ui.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import stallone.kouakou.detazo.agname.viewmodel.ArticleViewModel
import stallone.kouakou.detazo.agname.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    articleViewModel: ArticleViewModel,
    onCheckout: () -> Unit
) {
    val items = cartViewModel.items

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            Text("Mon panier", style = MaterialTheme.typography.titleLarge)

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(items) { item ->
                    CartItemRow(item = item, onRemove = {
                        cartViewModel.removeFromCart(item.article)
                    })
                }
            }

            Text("Total : ${cartViewModel.getTotalPrice()} FCFA")

            Row {
                Button(
                    onClick = {
                        cartViewModel.checkout(articleViewModel)
                        scope.launch {
                            snackbarHostState.showSnackbar("✅ Paiement effectué avec succès !")
                        }
                        onCheckout()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Payer")
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = { cartViewModel.clearCart() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Vider")
                }
            }
        }
    }
}
