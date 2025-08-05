package stallone.kouakou.detazo.agname.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import stallone.kouakou.detazo.agname.data.entites.Sale
import stallone.kouakou.detazo.agname.viewmodel.SaleViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaleScreen(saleViewModel: SaleViewModel) {
    val saleList = remember { mutableStateListOf<Sale>() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            saleViewModel.getAllSales().collectLatest { sales ->
                saleList.clear()
                saleList.addAll(sales)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historique des ventes") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (saleList.isEmpty()) {
                item {
                    Text("Aucune vente enregistrée.")
                }
            } else {
                items(saleList) { sale ->
                    SaleItem(sale)
                }
            }
        }
    }
}

@Composable
fun SaleItem(sale: Sale) {
    val dateFormatted = remember(sale.date) {
        SimpleDateFormat("dd MMM yyyy à HH:mm", Locale.getDefault()).format(Date(sale.date))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("ID Article : ${sale.articleId}", style = MaterialTheme.typography.titleSmall)
            Text("Quantité vendue : ${sale.quantitySold}")
            Text("Date : $dateFormatted")
        }
    }
}
