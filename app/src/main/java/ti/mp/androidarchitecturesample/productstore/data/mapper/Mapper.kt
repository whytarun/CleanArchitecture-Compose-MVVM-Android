package ti.mp.androidarchitecturesample.productstore.data.mapper


import retrofit2.HttpException
import ti.mp.androidarchitecturesample.productstore.domain.model.ApiError
import ti.mp.androidarchitecturesample.productstore.domain.model.NetworkError
import java.io.IOException

fun Throwable.toNetworkError() :NetworkError
{
    val error = when(this){
        is IOException -> ApiError.NetworkError
        is HttpException -> ApiError.UnknownResponse
        else -> ApiError.UnknownError
    }
    return NetworkError(
        error =error,
         t =this
    )
}