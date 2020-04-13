package com.example.newsreader.di

import android.content.Context
import android.util.Log
import com.example.newsreader.BuildConfig
import com.example.newsreader.data.MainModel
import com.example.newsreader.data.MainMvpModel
import com.example.newsreader.data.local.interfaces.INewsLocalDb
import com.example.newsreader.data.local.realm.RealmNewsLocalDb
import com.example.newsreader.data.local.realm.RmDatabaseModule
import com.example.newsreader.data.remote.newsapi.NewsApi
import com.example.newsreader.data.remote.retrofit.NewsApiService
import com.example.newsreader.data.remote.retrofit.RetrofitNewsApi
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    /**
     * Provide NewsApi interface for fetching news from remote api.
     */
    single<NewsApi> { createRetrofitNewsApi(androidContext()) }

    /**
     * Provide NewsLocalDb interface from accessing local database.
     */
    single<INewsLocalDb> { createRealmNewsLocalDb() }

    /**
     * Provide MainMvpModel interface for accessing local and remote data sources ( repository ).
     */
    single<MainMvpModel> { MainModel(get(), get()) }

}

/**
 * Create NewsApi interface for fetching news from remote api.
 *
 * @param context Android context.
 * @return NewsApi interface.
 */
private fun createRetrofitNewsApi(context: Context): NewsApi {
    val interceptor: HttpLoggingInterceptor = createHttpLoggingInterceptor()

    val httpClient: OkHttpClient.Builder = createOkHttpClientBuilder(interceptor)

    val retrofit: Retrofit = createRetrofit(httpClient)

    return RetrofitNewsApi(retrofit.create(NewsApiService::class.java), context.resources)
}

/**
 * Create logging interceptor for Retrofit.
 *
 * @return HttpLoggingInterceptor object.
 */
private fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val logger: HttpLoggingInterceptor.Logger = object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.d("OkHttp", message)
        }
    }

    val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor(logger)
    interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE

    return interceptor
}

/**
 * Create OkHttp client builder for Retrofit.
 *
 * @return OkHttpClient.Builder object.
 */
private fun createOkHttpClientBuilder(interceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
    val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    httpClient.addInterceptor(interceptor)

    return httpClient
}

/**
 * Create Retrofit.
 *
 * @return Retrofit object.
 */
private fun createRetrofit(httpClient: OkHttpClient.Builder): Retrofit {
    return Retrofit.Builder()
        .baseUrl(NewsApiService.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build()
}

/**
 * Create main class for accessing Room database.
 */
private fun createRealmNewsLocalDb(): RealmNewsLocalDb {
    val configuration: RealmConfiguration = RealmConfiguration.Builder()
        .name("newsDatabase.realm")
        .modules(RmDatabaseModule())
        .deleteRealmIfMigrationNeeded()
        .build()

    return RealmNewsLocalDb(configuration)
}
