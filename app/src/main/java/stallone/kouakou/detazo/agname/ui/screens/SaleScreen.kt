//package stallone.kouakou.detazo.agname.ui.screens.sales
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import kotlinx.coroutines.flow.collectLatest
//import stallone.kouakou.detazo.agname.data.entites.Sale
//import stallone.kouakou.detazo.agname.viewmodel.ArticleViewModel
//import stallone.kouakou.detazo.agname.viewmodel.SaleViewModel
//import java.text.SimpleDateFormat
//import java.util.*
//
//@Composable
//fun SalesScreen(
//    saleViewModel: SaleViewModel,
//    articleViewModel: ArticleViewModel
//) {
//    val sales by saleViewModel.getAllSales().collectAsState(initial = emptyList())
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text("Historique des ventes", style = MaterialTheme.typography.titleLarge)
//
//        LazyColumn {
//            items(sales) { sale ->
//                val article = articleViewModel.getArticleById(sale.articleId).collectAsState(initial = null).value
//
//                Card(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp)
//                ) {
//                    Column(modifier = Modifier.padding(8.dp)) {
//                        Text("Article: ${article?.name ?: "Chargement..."}")
//                        Text("Quantité vendue: ${sale.quantitySold}")
//                        Text("Date: ${formatDate(sale.date)}")
//                    }
//                }
//            }
//        }
//    }
//}
//
//fun formatDate(timestamp: Long): String {
//    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
//    return sdf.format(Date(timestamp))
//}
