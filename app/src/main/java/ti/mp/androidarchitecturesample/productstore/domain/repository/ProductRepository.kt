package ti.mp.androidarchitecturesample.productstore.domain.repository

import arrow.core.Either
import ti.mp.androidarchitecturesample.productstore.domain.model.NetworkError
import ti.mp.androidarchitecturesample.productstore.domain.model.Product

interface ProductRepository {

    suspend fun getProducts() :Either<NetworkError, List<Product>>
}