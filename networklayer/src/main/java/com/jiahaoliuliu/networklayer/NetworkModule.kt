package com.jiahaoliuliu.networklayer

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    private val retrofit: Retrofit

    companion object {
        private const val BASE_URL = ""
    }

    init {
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    fun provideItemService(): ItemsService {
        return retrofit.create(ItemsService::class.java)
    }
}