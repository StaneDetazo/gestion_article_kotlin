package stallone.kouakou.detazo.agname.data.entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "sales")
data class Sale(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val articleId: Int,
    val quantitySold: Int,
    val date: Long = System.currentTimeMillis()
)