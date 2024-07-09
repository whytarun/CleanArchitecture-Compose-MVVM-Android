package ti.mp.androidarchitecturesample

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import ti.mp.androidarchitecturesample.productstore.presentation.product_screen.ProductViewState
import ti.mp.androidarchitecturesample.productstore.presentation.product_screen.ProductsContent
import ti.mp.androidarchitecturesample.productstore.presentation.util.ContentDescriptions
import ti.mp.androidarchitecturesample.ui.theme.AndroidArchitectureSampleTheme
import ti.mp.androidarchitecturesample.util.MockData

class ProductScreenTest {
    @get:Rule
    val testRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun initialState_isRendered() {
        testRule.setContent {
            AndroidArchitectureSampleTheme {
                ProductsContent(
                    ProductViewState(
                        true,
                        emptyList()
                    )
                )
            }
        }
        testRule.onNodeWithContentDescription(
            ContentDescriptions.LOADING_INDICATOR
        ).assertIsDisplayed()
    }

    @Test
    fun contentLoadState_isRendered(){
        val stores =MockData.getStoreData()
        testRule.setContent {
            AndroidArchitectureSampleTheme{
                ProductsContent(state = ProductViewState(
                    false,
                    products = stores
                ))

            }
        }

       // testRule.onNodeWithText(stores[0].description).assertIsDisplayed()

                testRule.onNodeWithContentDescription(
            ContentDescriptions.LOADING_INDICATOR
        ).assertDoesNotExist()
    }


}