package ir.alirezanazari.data.net


import ir.alirezanazari.data.BuildConfig
import ir.alirezanazari.data.utils.Consts.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiConfig {

    companion object {

        operator fun invoke(
            interceptor: RequestInterceptor
        ): RestApi {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(16, TimeUnit.SECONDS)


            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                okHttpClient.addInterceptor(httpLoggingInterceptor)
            }

            return Retrofit.Builder()
                .client(okHttpClient.build())
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RestApi::class.java)

        }

    }

}
