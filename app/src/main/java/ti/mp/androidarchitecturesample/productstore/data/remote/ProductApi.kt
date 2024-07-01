package ti.mp.androidarchitecturesample.productstore.data.remote

import retrofit2.http.GET
import ti.mp.androidarchitecturesample.productstore.domain.model.Product

interface ProductApi {
    @GET("products")
    suspend fun getProducts():List<Product>
}