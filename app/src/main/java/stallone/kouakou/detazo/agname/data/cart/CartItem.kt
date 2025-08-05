package stallone.kouakou.detazo.agname.data.cart

import stallone.kouakou.detazo.agname.data.entites.Article

data class CartItem(
    val article: Article,
    var quantity: Int = 1
) {
    val totalPrice: Double
        get() = quantity * article.price
}
