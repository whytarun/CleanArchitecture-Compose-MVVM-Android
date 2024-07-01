package ti.mp.androidarchitecturesample.productstore.data.repository

import arrow.core.Either
import ti.mp.androidarchitecturesample.productstore.data.mapper.toNetworkError
import ti.mp.androidarchitecturesample.productstore.data.remote.ProductApi
import ti.mp.androidarchitecturesample.productstore.domain.model.NetworkError
import ti.mp.androidarchitecturesample.productstore.domain.model.Product
import ti.mp.androidarchitecturesample.productstore.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val productApi: ProductApi) :ProductRepository {
    override suspend fun getProducts(): Either<NetworkError, List<Product>> {
        return Either.catch {
            productApi.getProducts()
        }.mapLeft { it.toNetworkError() }
    }
}