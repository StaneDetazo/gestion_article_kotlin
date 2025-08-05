package stallone.kouakou.detazo.agname.ui.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import stallone.kouakou.detazo.agname.data.cart.CartItem

@Composable
fun CartItemRow(item: CartItem, onRemove: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(item.article.name)
            Text("${item.quantity} x ${item.article.price} FCFA")
        }
        IconButton(onClick = onRemove) {
            Icon(Icons.Default.Delete, contentDescription = "Retirer")
        }
    }
}
