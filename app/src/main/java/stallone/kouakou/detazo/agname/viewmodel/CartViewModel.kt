package stallone.kouakou.detazo.agname.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import stallone.kouakou.detazo.agname.data.cart.CartItem
import stallone.kouakou.detazo.agname.data.entites.Article

class CartViewModel : ViewModel() {
    private val _items = mutableStateListOf<CartItem>()
    val items: List<CartItem> get() = _items

    fun addToCart(article: Article) {
        val existingItem = _items.find { it.article.id == article.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            _items.add(CartItem(article))
        }
    }

    fun removeFromCart(article: Article) {
        val item = _items.find { it.article.id == article.id }
        if (item != null) {
            _items.remove(item)
        }
    }

    fun clearCart() {
        _items.clear()
    }

    fun getTotalPrice(): Double {
        return _items.sumOf { it.totalPrice }
    }

//    fun checkout(articleViewModel: ArticleViewModel) {
//        for (item in items) {
//            val article = item.article
//            val newQuantity = article.quantity - item.quantity
//
//            // Mise à jour locale de la quantité
//            article.quantity = newQuantity
//
//            // Mise à jour via le viewModel (base de données ou state)
//            articleViewModel.update(article)
//        }
//
//        clearCart() // Vider le panier après paiement
//    }
fun checkout(articleViewModel: ArticleViewModel, saleViewModel: SaleViewModel) {
    for (item in items) {
        val article = item.article
        val newQuantity = (article.quantity - item.quantity).coerceAtLeast(0)

        article.quantity = newQuantity
        articleViewModel.update(article)

        // Enregistrer la vente !
        saleViewModel.sellArticle(article.id, item.quantity)
    }
    clearCart()
}

}
