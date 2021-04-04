package mashup.data.exception

import android.util.Log
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleSource
import io.reactivex.rxjava3.core.SingleTransformer
import mashup.data.response.RESULT
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException

class NetworkExceptionSingleTransformer<T> : SingleTransformer<T, T> {

    private val tag = "NetworkException"

    override fun apply(upstream: Single<T>): SingleSource<T> =
        upstream.onErrorResumeNext {
            Log.d(tag, "onErrorResumeNext : $it")
            Log.d(tag, "onErrorResumeNext message : ${it.message}")
            val status = it.message?.trim()?.takeLast(3)?.toInt() ?: 400

            Single.error(
                if (it is HttpException) {
                    it.response()?.errorBody()?.let { errorBody ->
                        val jsonString = errorBody.string()
                        var description = ""

                        try {
                            val jsonObject = JSONObject(jsonString)
                            Log.d(tag, "jsonObject : $jsonObject")
                            description = jsonObject.optString("description")
                            Log.d(tag, "status : $status, description : $description")
                        } catch (e: JSONException) {
                            Log.e(tag, e.message ?: "")
                        }

                        if (status == RESULT.REFRESH_TOKEN.code) {
                            Exception.AuthenticationException(message = description, code = status)
                        } else {
                            Exception.MACHttpException(message = description, code = status)
                        }
                    }
                } else it
            )
        }
}

internal fun <T> Single<T>.composeError() =
    compose(NetworkExceptionSingleTransformer())