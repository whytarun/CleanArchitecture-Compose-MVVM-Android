package ti.mp.androidarchitecturesample

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import ti.mp.androidarchitecturesample.productstore.data.repository.ProductRepositoryImpl
import ti.mp.androidarchitecturesample.productstore.presentation.product_screen.ProductViewModel
import ti.mp.androidarchitecturesample.productstore.presentation.product_screen.ProductViewState
import ti.mp.androidarchitecturesample.util.MockData
@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {
    private lateinit var viewModel: ProductViewModel
    private lateinit var fakeProductApi: FakeProductApi
    private lateinit var productRepository: ProductRepositoryImpl
    private val testDispatcher = StandardTestDispatcher()
   // val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeProductApi = FakeProductApi()
        productRepository = ProductRepositoryImpl(fakeProductApi)
        viewModel = ProductViewModel(productRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialState_isProduced() = runTest {
        val initialState = viewModel.state.value
        assertEquals(
            ProductViewState(
                products = emptyList(),
                isLoading = true,
                error = null
            ),
            initialState
        )
    }

    @Test
    fun contentLoadedState_isProduced() =runTest {
        advanceUntilIdle()
        val currentState = viewModel.state.value
        assertEquals(
            ProductViewState(
                products = MockData.getRemoteData(),
                isLoading = false,
                error = null
            ),
            currentState
        )
    }

    @Test
    fun errorState_isProduced() =runTest {
        fakeProductApi = FakeProductApi(shouldReturnError = true)
        productRepository = ProductRepositoryImpl(fakeProductApi)
        viewModel = ProductViewModel(productRepository)
        advanceUntilIdle()
        val currentState = viewModel.state.value
        assertEquals(
            ProductViewState(
                products = emptyList(),
                isLoading = false,
                error = "Error"
            ),
            currentState
        )
    }

}