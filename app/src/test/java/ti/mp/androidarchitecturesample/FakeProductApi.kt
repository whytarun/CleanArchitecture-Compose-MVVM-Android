package ti.mp.androidarchitecturesample

import kotlinx.coroutines.time.delay
import ti.mp.androidarchitecturesample.productstore.data.remote.ProductApi
import ti.mp.androidarchitecturesample.productstore.domain.model.Product
import ti.mp.androidarchitecturesample.util.MockData
import java.time.Duration

class FakeProductApi(private val shouldReturnError: Boolean = false) :ProductApi {
    override suspend fun getProducts(): List<Product> {
        if (shouldReturnError) {
            throw Exception("Error")
        }
        delay(Duration.ofMillis(500))
        return MockData.getRemoteData()

    }
}