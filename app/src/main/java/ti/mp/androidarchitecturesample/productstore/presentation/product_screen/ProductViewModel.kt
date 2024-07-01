package ti.mp.androidarchitecturesample.productstore.presentation.product_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ti.mp.androidarchitecturesample.productstore.domain.repository.ProductRepository
import ti.mp.androidarchitecturesample.productstore.presentation.util.sendEvent
import ti.mp.androidarchitecturesample.util.Event
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) :ViewModel() {

        private val _state = MutableStateFlow(ProductViewState())
        val state =_state.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts(){
        viewModelScope.launch {
            _state.update{
                it.copy(isLoading = true)
            }
            productRepository.getProducts()
                .onRight { products ->
                    _state.update {
                        it.copy(products =products)
                    }
                }.onLeft { error ->
                    _state.update {
                        it.copy(
                            error = error.error.message
                        )
                    }
                    sendEvent(Event.Toast(error.error.message))
                }
            _state.update {
                it.copy(isLoading = false)
            }
        }
    }
}