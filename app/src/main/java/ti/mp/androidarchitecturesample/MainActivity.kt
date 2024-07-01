package ti.mp.androidarchitecturesample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import ti.mp.androidarchitecturesample.productstore.presentation.product_screen.ProductScreen
import ti.mp.androidarchitecturesample.ui.theme.AndroidArchitectureSampleTheme
import ti.mp.androidarchitecturesample.util.Event
import ti.mp.androidarchitecturesample.util.EventBus

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidArchitectureSampleTheme {
               val lifecycle = LocalLifecycleOwner.current.lifecycle
                LaunchedEffect(key1 = lifecycle)
                {
                    repeatOnLifecycle(state =Lifecycle.State.STARTED){
                        EventBus.events.collect{ event ->
                            if( event is Event.Toast){
                                Toast.makeText(this@MainActivity, event.message,Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductScreen()
                }
            }
        }
    }
}

