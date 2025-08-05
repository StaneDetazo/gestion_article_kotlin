package stallone.kouakou.detazo.agname.data.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String,
    val price: Double,
    var quantity: Int,
    val description: String? = null,
    val isActive: Boolean = true
)