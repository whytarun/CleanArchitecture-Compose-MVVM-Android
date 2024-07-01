package ti.mp.androidarchitecturesample.productstore.domain.model

import android.media.Rating

data class Product(
    val id :Int,
    val title :String,
    val description :String,
    val category: String,
    val image :String,
    val rating :ti.mp.androidarchitecturesample.productstore.domain.model.Rating
)

data class Rating(
    val range :Double,
    val count :Int
)
