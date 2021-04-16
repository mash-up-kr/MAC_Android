package mashup.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {

    private const val BASE_URL = "https://api.cowcat.live/api/v1/"

    fun <T> provideApi(
        service: Class<T>,
    ): T = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient(provideLoggingInterceptor()))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(service)

    fun <T> provideRefreshApi(
        service: Class<T>,
    ): T = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient(provideLoggingInterceptor(), true))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(service)

    fun <T> provideApiWithoutHeader(
        service: Class<T>,
    ): T = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClientWithoutHeader(provideLoggingInterceptor()))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(service)

    // 네트워크 통신에 사용할 클라이언트 객체를 생성합니다.
    private fun provideOkHttpClientWithoutHeader(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val b = OkHttpClient.Builder()
        // 이 클라이언트를 통해 오고 가는 네트워크 요청/응답을 로그로 표시하도록 합니다.
        b.addInterceptor(interceptor)

        return b.build()
    }

    private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor, refreshToken: Boolean = false): OkHttpClient {
        val b = OkHttpClient.Builder()
        // 이 클라이언트를 통해 오고 가는 네트워크 요청/응답을 로그로 표시하도록 합니다.
        b.addInterceptor(interceptor)

        // header 에 정보를 추가해 준다.
        b.addInterceptor(AddHeaderInterceptor(refreshToken))

        return b.build()
    }

    // 네트워크 요청/응답을 로그에 표시하는 Interceptor 객체를 생성합니다.
    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        return interceptor
    }
}