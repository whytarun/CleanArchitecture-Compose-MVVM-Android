package ti.mp.androidarchitecturesample.productstore.presentation.product_screen

import ti.mp.androidarchitecturesample.productstore.domain.model.Product

data class ProductViewState(
    val isLoading :Boolean =false,
    val products :List<Product> = emptyList(),
    val error: String? = null
)