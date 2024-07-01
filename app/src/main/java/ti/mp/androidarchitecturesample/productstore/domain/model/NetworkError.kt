package ti.mp.androidarchitecturesample.productstore.domain.model

import android.webkit.ConsoleMessage

data class NetworkError(
    val error: ApiError,
    val t:Throwable ? =null
)

enum class ApiError(val message: String){
    NetworkError("Network Error"),
    UnknownResponse("Unknown Response"),
    UnknownError("Unknown Error")

}
