package com.mirz.storyapp.data.source.remote

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mirz.storyapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder(private val dataStore: DataStore<Preferences>) {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .addInterceptor(AuthInterceptor(dataStore))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiServices = getRetrofit().create(ApiServices::class.java)

}