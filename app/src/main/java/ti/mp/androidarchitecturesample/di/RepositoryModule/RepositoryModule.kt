package ti.mp.androidarchitecturesample.di.RepositoryModule

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ti.mp.androidarchitecturesample.productstore.data.repository.ProductRepositoryImpl
import ti.mp.androidarchitecturesample.productstore.domain.repository.ProductRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl):ProductRepository
}