package ru.tradernet.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.tradernet.data.api.Api
import ru.tradernet.data.repositories.TikerRepositoryImpl
import ru.tradernet.domain.interfaces.TikerRepository
import timber.log.Timber
import java.util.concurrent.TimeUnit

object DataModule {

    private const val BASE_URL = "https://api.github.com/"

    fun create() = module {
        single {
            createApi(
                createHttpClient(),
                get()
            )
        }

        single { createMoshi() }

        factory { TikerRepositoryImpl() as TikerRepository }
    }

    private fun createApi(client: OkHttpClient, moshi: Moshi): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(Api::class.java)
    }

    private fun createHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        with(builder) {

            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)

            addNetworkInterceptor(loggingInterceptor())
        }
        return builder.build()
    }

    private fun loggingInterceptor(): Interceptor {
        val logger = object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag("OkHttp").d(message)
            }
        }
        return HttpLoggingInterceptor(logger).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private fun createMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}