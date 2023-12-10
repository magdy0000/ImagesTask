package com.beyond.imagetask.di

import com.beyond.imagetask.data.network.Services
import com.beyond.imagetask.data.repository.Repository
import com.beyond.imagetask.data.utils.API_KEY
import com.beyond.imagetask.data.utils.BASE_URL
import com.beyond.imagetask.domian.repository.IRepository
import com.beyond.imagetask.domian.usecases.filter.FilterUseCase
import com.beyond.imagetask.domian.usecases.filter.IFilterUseCase
import com.beyond.imagetask.domian.usecases.getimages.IImagesUseCase
import com.beyond.imagetask.domian.usecases.getimages.ImagesUseCase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .callTimeout(50, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val originalRequest = chain.request()
                    val originalUrl = originalRequest.url
                    val url = originalUrl.newBuilder()
                        .addQueryParameter("api_key", API_KEY)
                        .build()
                    val requestBuilder = originalRequest.newBuilder().url(url)
                        .addHeader("Accept", "application/json")
                    val request = requestBuilder.build()
                    val response = chain.proceed(request)

                    return response
                }
            })
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): Services {
        return retrofit.create(Services::class.java)
    }

    @Provides
    fun provideRepository(services: Services): IRepository{

        return Repository(services)
    }
    @Provides
    fun provideUseCase (repository: IRepository) : IImagesUseCase {
        return ImagesUseCase(repository)
    }
    @Provides
    fun provideIFilterUseCase() : IFilterUseCase {
        return FilterUseCase()
    }
}